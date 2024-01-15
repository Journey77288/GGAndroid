package io.ganguo.pay.alipay;

import android.text.TextUtils;

final class PayData {
    private String resultStatus;
    private String result;
    private String memo;

    public PayData(String rawResult) {
        if (TextUtils.isEmpty(rawResult))
            return;

        String[] resultParams = rawResult.replace("{", "").replace("}", "").split(",");

        for (String resultParam : resultParams) {
            String resultTrim = resultParam.trim();

            if (resultTrim.startsWith("resultStatus")) {
                resultStatus = gatValue(resultTrim, "resultStatus");
            }
            if (resultTrim.startsWith("result")) {
                result = gatValue(resultTrim, "result");
            }
            if (resultTrim.startsWith("memo")) {
                memo = gatValue(resultTrim, "memo");
            }
        }
    }

    private String gatValue(String content, String key) {
        String prefix = key + "=";
        return content.substring(content.indexOf(prefix) + prefix.length(), content.length());
    }

    @Override
    public String toString() {
        return "resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}";
    }

    /**
     * @return the resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
}