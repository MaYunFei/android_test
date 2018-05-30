package com.yunfei.download;

import org.greenrobot.greendao.converter.PropertyConverter;

public enum Status {
    WATTING(0),
    DOWLOADING(1),
    PAUSE(2),
    ERROR(3),
    FINISH(4);
    final int id; // 使用稳定的 id 来转换，不要使用不稳定的名字和顺序

    Status(int id) {
        this.id = id;
    }

    public static class StatusConverter implements PropertyConverter<Status, Integer> {
        @Override
        public Status convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (Status role : Status.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return Status.WATTING; // 准备一个默认值，防止数据移除时崩溃
        }

        @Override
        public Integer convertToDatabaseValue(Status entityProperty) {
            // 判断返回 null
            return entityProperty == null ? null : entityProperty.id;
        }
    }
}


