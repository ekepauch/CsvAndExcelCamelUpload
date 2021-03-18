package com.cpay.fileUpload.util;



import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.*;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Utility {


    public static final String APP_NAME = "Cpay-file-upload";
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    private static SecureRandom number = null;
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NO = "0123456789";
    private static Random rnd = new Random();
    private static String terminalIdValue;
    private static Integer tranCounter = 0;

    static {
        try {
            number = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            log.error("generateRandom Exception: " + e.getMessage());
            LoggerUtil.logError(logger, e);
        }
    }


    public static String batchId(String batchId) {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmssss");
       // DateFormat df = new SimpleDateFormat("yyMMddHH");
        String datePart = df.format(new Date());
        return batchId + datePart;
    }

    public static String batchIds() {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmssss");
        String datePart = df.format(new Date());
        return  datePart;
    }





    public static String fileContentsToString(String file) {
        String contents = "";

        File f;
        try {
            f = new File(file);

            if (f.exists()) {
                FileReader fr = null;
                try {
                    fr = new FileReader(f);
                    char[] template = new char[(int) f.length()];
                    fr.read(template);
                    contents = new String(template);
                } catch (Exception e) {
                    log.error("fileContentsToString Exception Inner: " + e.getMessage());
                    LoggerUtil.logError(logger, e);
                } finally {
                    if (fr != null) {
                        fr.close();
                    }
                }
            }
        } catch (Exception e) {
            log.error("fileContentsToString Exception: " + e.getMessage());
            LoggerUtil.logError(logger, e);
        }
        return contents;
    }


    public static boolean containSpecialCharacter(String unsafeInput) {

        boolean specialCharacterFound = false;
        Pattern p = Pattern.compile("[^A-Za-z0-9-']");
        Matcher m = p.matcher(unsafeInput);
        if (m.find()) {
            specialCharacterFound = true;
        }
        return specialCharacterFound;
    }


    public static Date convertDate(String dateString) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
        Date date = format1.parse(dateString);
        return date;
    }

    public static boolean validEmail(String email) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(EMAIL_REGEX))
            return true;
        else
            return false;
    }


    public static boolean isNumeric(String number) {
        boolean isValid = false;

        String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public static String getSaltString() {
        return UUID.randomUUID().toString().substring(0, 5) + UUID.randomUUID().toString().substring(0, 3);
    }


    public static String guidID() {

        return UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }

    public static String next(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String getIpAddress(HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");
        ipAddress = ipAddress == null ? httpRequest.getRemoteAddr() : ipAddress;
        return ipAddress;
    }

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }


    public static String getServerURl(HttpServletRequest request, boolean behindProxy, String proxyName) {
        String uri = null;
        try {

            String serverName;

            if (behindProxy) {// If a X-Forwarded-Host-Header is present (e.g. running behind proxy server)
                serverName = proxyName;
            } else { //else no proxy is configured, we can go ahead and pick the servername name from the request
                //append the servername with the port number
                serverName = request.getServerName() +
                        (request.getServerPort() > 0 ? ":" + request.getServerPort() : "");
            }
            uri = request.getScheme() + "://" +   // "http" + "://
                    serverName;
            // + request.getRequestURI() +       // "/people"
            //(request.getQueryString() != null ? "?" +
            //    request.getQueryString() : ""); // "?" + "lastname=Fox&age=30"
        } catch (Exception e) {
            log.error("getServerURl Exception: " + e.getMessage());
            LoggerUtil.logError(logger, e);
        }
        return uri;
    }


    public static boolean containsNummber(String inputStr) {
        String pattern = "^[a-zA-Z]*$";
        return !inputStr.matches(pattern);


    }

    public static boolean validateName(String name) {
        String pattern = "^[a-zA-Z-']*$";
        return name.matches(pattern);
    }


    public static String stringRemoveRightmost(String str, int len) {

        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, str.length() - len);
    }


    public static String rightMost(String str, int len) {

        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String leftMost(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }


//    public static Integer generateRandom(int length) {
//
//        if (number != null) {
//            return number.nextInt(length);
//        }
//        throw new RandomGeneratorUnavailableException("10060", "number generator is null or uninitialized");
//    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }


    public static String convertToSha1(String value) {
        String retVal = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(value.getBytes());
            byte[] output = md.digest();
            retVal = bytesToHex(output);
        } catch (Exception e) {
            log.error("convertToSha1 Exception: " + e.getMessage());
            LoggerUtil.logError(logger, e);
        }
        return retVal;
    }

    public static String convertToSha512(String valueToHash) {

        String retVal = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(valueToHash.getBytes());
            byte[] output = md.digest();
            retVal = bytesToHex(output);
        } catch (Exception e) {
            log.error("convertToSha-512 Exception: " + e.getMessage());
            LoggerUtil.logError(logger, e);
        }
        return retVal;
    }


    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString().toLowerCase();
    }


    public static String lPad(String str, Integer length, char replace) {

        return String.format("%" + (length - str.length()) + "s", "")
                .replace(" ", String.valueOf(replace))
                +
                str;
    }


    public static boolean hasValidName(String name) {


        String pattern = "^[a-zA-Z-]*$";
        if (name.matches(pattern)) {
            return true;
        }
        return false;

    }


    public static int computeAge(String dateOfBirth) throws Exception {

        final Long MILLI_SECONDS_YEAR = 31558464000L;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date dbDate = dateFormat.parse(dateOfBirth);

        // Compute age.
        long timeDiff = System.currentTimeMillis() - dbDate.getTime();
        int age = (int) (timeDiff / MILLI_SECONDS_YEAR);  //
        return age;
    }

    public static boolean isAlphaNumeric(String s) {
        String pattern = "^[a-zA-Z0-9]*$";
        if (s.matches(pattern)) {
            return true;
        }
        return false;
    }


//    public static boolean validateNumbers(String unsafePhone) {
//        Pattern pattern = Pattern.compile("\\d+"); //accepts only digits
//        Matcher matcher = pattern.matcher(unsafePhone);
//        boolean valid = false;
//        if (matcher.matches()) {
//            valid = true;
//        }
//        return valid;
//    }


    public static boolean validateFloat(String unsafePhone) {

        Pattern pattern = Pattern.compile("^[+-]?([0-9]*[.])?[0-9]+"); //accepts only floats
        Matcher matcher = pattern.matcher(unsafePhone);

        boolean valid = false;

        if (matcher.matches()) {
            valid = true;
        }

        return valid;
    }



    public static boolean validatePhoneNumber(String unsafePhone) {

        Pattern pattern = Pattern.compile("\\d+"); //accepts only digits
        Matcher matcher = pattern.matcher(unsafePhone);

        boolean valid = false;

        if (matcher.matches()) {
            valid = true;
        }

        return valid;
    }

    public static boolean containsAlphabet(String unsafeInput) {
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
        Matcher matcher = pattern.matcher(unsafeInput);
        boolean valid = false;
        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

    public static String getCurrency(String currency) { //TODO CHECK FOR CORRECT CODE
        String code = "";
        if (!currency.isEmpty()) {
            switch (currency.toLowerCase()) {
                case "ngn":
                    code = "566";
                    break;
                case "usd":
                    code = "840";
                    break;
                case "gbp":
                    code = "826";
                    break;
                case "eur":
                    code = "978";
                    break;
                default:
                    code = "566";
                    break;

            }
        }

        return code;

    }


    public static String currencyActualFormat(String currency, String amount) {
        amount = amount.replaceAll("[+.^:,]", "");

        String code = "";
        String pattern = "###,###.##";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(Double.valueOf(amount));
        if (!currency.isEmpty()) {
            switch (currency.toLowerCase()) {
                case "ngn":
                    code = "₦";
                    break;
                case "usd":
                    code = "$";
                    break;
                case "gbp":
                    code = "£";
                    break;
                case "eur":
                    code = "€";
                    break;
                default:
                    code = currency;
                    break;

            }
        }

        return String.format("%1s%2s", code, output);
    }

    public static Double convertToDouble(String value) {
        Double data = 0.0;
        if (value != null && !value.isEmpty() && value.length() > 0) {
            data = Double.parseDouble(value);
        }
        return data;
    }


// amount should be in string then it validate to 2 decimal places nd accepts only digits
    public static boolean validateDecimalCharacter(String decimalCharacter) {
        boolean validFormat = false;
        try {
            // String regex = "^\\d{1,10}\\.\\d{0,2}$"; this has a decimal places of upto 2 e.g. 0,1,2

            String regex = "^\\d{1,10}\\.\\d{2}$";//this has 2 decimal places
            validFormat = decimalCharacter.matches(regex);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return validFormat;
    }


    public static boolean validateString(String name) {
        String pattern = "^[a-z A-Z 0-9 & @ $ # . : ; , - '-]*$";
        return name.matches(pattern);
    }


    public String toXml(Object ob) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ob.getClass());
        Marshaller marshaller = jc.createMarshaller();
        //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(ob, stringWriter);
        String xml = stringWriter.getBuffer().toString();
        return xml;
    }

    public <F> F fromXml(Class<F> clazz, String xml) throws JAXBException {
        Unmarshaller unmarshaller = null;
        Source xmlSource = null;
        JAXBElement<F> unmarshal = null;
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(new StringReader(xml)));
            JAXBContext jc = JAXBContext.newInstance(clazz);
            unmarshaller = jc.createUnmarshaller();
            unmarshal = unmarshaller.unmarshal(xmlSource, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (unmarshal != null) ? unmarshal.getValue() : null;
    }


    public static String doRandomPassword(int len) {
        String resp = "";
        try {
            Random rd = new Random();
            final String alphabet = "1234567890!@$%^&*()~ASQWERTYUOPDFGHJKZXVBNM?qwertyuopasdfghjkzxcvbnm";
            final int N = alphabet.length();
            int iLength = len;
            StringBuilder sb = new StringBuilder(iLength);
            for (int i = 0; i < iLength; i++) {
                sb.append(alphabet.charAt(rd.nextInt(N)));
            }
            resp = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;
    }

}
