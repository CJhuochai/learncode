package com.example.demo.entity;

public enum DayOfWeekEnum {
    SUNDAY(new I18nValue("Sunday", "星期日"), new I18nValue("Sun", "周日"), 1),
    MONDAY(new I18nValue("Monday", "星期一"), new I18nValue("Mon", "周一"), 2),
    TUESDAY(new I18nValue("Tuesday", "星期二"), new I18nValue("Tue", "周二"), 3),
    WEDNESDAY(new I18nValue("Wednesday", "星期三"), new I18nValue("Wed", "周三"), 4),
    THURSDAY(new I18nValue("Thursday", "星期四"), new I18nValue("Thu", "周四"), 5),
    FRIDAY(new I18nValue("Friday", "星期五"), new I18nValue("Fri", "周五"), 6),
    SATURDAY(new I18nValue("Saturday", "星期六"), new I18nValue("Sat", "周六"), 7);
    private I18nValue alias;
    private I18nValue abbreviation;
    private int index;

    private DayOfWeekEnum(I18nValue alias, I18nValue abbreviation, int index) {
        this.alias = alias;
        this.abbreviation = abbreviation;
        this.index = index;
    }

    public static DayOfWeekEnum getInstance(int index) {
        for (DayOfWeekEnum week : DayOfWeekEnum.values()) {
            if (week.getIndex() == index) {
                return week;
            }
        }
        throw new RuntimeException("系统异常index='" + index + "'");
    }



    public I18nValue getAlias() {
        return alias;
    }

    public I18nValue getAbbreviation() {
        return abbreviation;
    }

    public Integer getIndex() {
        return index;
    }

}
