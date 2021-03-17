package kr.co.triphos.scarecrow.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final DecimalFormat commaThree = new DecimalFormat("#,###.####");

    /**
     * Null 체크
     */
    public static boolean isNull(String arg) {
        boolean isNull = false;
        if(arg == null) {
            isNull = true;
        }

        return isNull;
    }

    /**
     * Null 체크 "0" 반환
     */
    public static String isNullZero(String arg) {
        if(isNull(arg)) return "0";

        return arg.trim();
    }

    /**
     * Null 체크 "" 반환
     */
    public static String isNullEmpty(String arg) {
        if(isNull(arg)) return "";

        return arg.trim();
    }

    /**
     * Null 체크 Default값 반환
     */
    public static String isNullDefault(String arg, String strDefault) {
        if(isNull(arg)) return strDefault;

        return arg.trim();
    }

    /**
     * int 형으로 변환
     * @param arg
     * @return
     */
    public static int convertInteger(String arg) {
        int rtnNumber = 0;
        if(! isNull(arg)) {
            try {
                rtnNumber = Integer.parseInt(arg);
            } catch (Exception e) {
                rtnNumber = 0;
            }
        }
        return rtnNumber;
    }

    /**
     * Double 형으로 변환
     * @param arg
     * @return
     */
    public static double convertDouble(String arg) {
        double rtnNumber = 0.0;
        if(! isNull(arg)) {
            try {
                rtnNumber = Double.parseDouble(arg);
            } catch (Exception e) {
                rtnNumber = 0.0;
            }
        }
        return rtnNumber;
    }

    /**
     * 3자리 콤마
     * @param arg
     * @return
     */
    public static String digit3Comma(String arg) {
        if(isNull(arg)) return "";

        arg = arg.replaceAll(",", "");
        return commaThree.format(convertDouble(arg));
    }

    /**
     * 정규식 변환
     * @param mTag
     * @param arg
     * @return
     */
    public static final String regexNumber = "num";
    public static final String regexEnglish = "eng";
    public static final String regexKorean = "kor";
    public static String getRegex(String lang, String arg) {
        String rtnStr = "";
        String sRegex = "";
        if (lang.equals(regexNumber)) {
            sRegex = "[^0-9]";
        }
        else if (lang.equals(regexEnglish)) {
            sRegex = "[^a-zA-Z]";
        }

        rtnStr = arg.replaceAll(sRegex,"");
        return rtnStr;
    }

    /**
     * if (StringUtil.getRegexMatche(StringUtil.regexEnglish, s.toString()) == false) {
     *      String str = StringUtil.getRegex(StringUtil.regexEnglish, s.toString());
     * 		mBinding.contractFirstName.setText(str);
     * 		mBinding.contractFirstName.setSelection(str.length());
     * }
     * @param lang
     * @param arg
     * @return
     */
    public static boolean getRegexMatche(String lang, String arg) {
        String sRegex = "";
        if (lang.equals(regexNumber)) {
            sRegex = "^[0-9]+$";
        }
        else if (lang.equals(regexEnglish)) {
            sRegex = "^[a-zA-Z]+$";
        }

        Pattern ps = Pattern.compile(sRegex);
        if (ps.matcher(arg).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 전화번호 형식으로 변경
     */
    public static String getTelNumber(String arg) {
        String telNumber = "";
        if(isNull(arg)) return telNumber;

        if (arg.length() == 8) {
            telNumber = arg.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        }
        else if (arg.length() == 12) {
            telNumber = arg.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        else {
            telNumber = arg.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
        }

        return telNumber;
    }

    /**
     * TODO 이메일 형식 확인
     */
    public static boolean isEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * printStackTrace to String
     * @param e
     * @return
     */
    public static String getPrintStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return errors.toString();
    }

    /**
     * TODO 파일명 확장자 가져오기
     * @param fileStr 파일명
     * @return 파일 확장자
     */
    public static String getExtension(String fileStr) {
        return fileStr.substring(fileStr.lastIndexOf(".") + 1);
    }

    /**
     * TODO MimeType 뱐환
     * @param file File
     * @return String
     */
    public static String getMimeType(File file) {
        String mimeType = "";
        String fileExtend = getExtension(file.getAbsolutePath()); // 확장자 구하기

        if (fileExtend.equalsIgnoreCase("mp3")) {
            mimeType = "audio/*";
        }
        else if (fileExtend.equalsIgnoreCase("mp4")) {
            mimeType = "video/*";
        }
        else if (fileExtend.equalsIgnoreCase("jpg") || fileExtend.equalsIgnoreCase("jpeg") || fileExtend.equalsIgnoreCase("gif") || fileExtend.equalsIgnoreCase("png") || fileExtend.equalsIgnoreCase("bmp")) {
            mimeType = "image/*";
        }
        else if (fileExtend.equalsIgnoreCase("txt")) {
            mimeType = "text/*";
        }
        else if (fileExtend.equalsIgnoreCase("doc") || fileExtend.equalsIgnoreCase("docx")) {
            mimeType = "application/msword";
        }
        else if (fileExtend.equalsIgnoreCase("xls") || fileExtend.equalsIgnoreCase("xlsx")) {
            mimeType = "application/vnd.ms-excel";
        }
        else if (fileExtend.equalsIgnoreCase("ppt") || fileExtend.equalsIgnoreCase("pptx")) {
            mimeType = "application/vnd.ms-powerpoint";
        }
        else if (fileExtend.equalsIgnoreCase("pdf")) {
            mimeType = "application/pdf";
        }
        else if (fileExtend.equalsIgnoreCase("hwp")) {
            mimeType = "application/haansofthwp";
        }

        return mimeType;
    }
}
