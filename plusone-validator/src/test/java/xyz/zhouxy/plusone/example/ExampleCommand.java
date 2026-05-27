/*
 * Copyright 2025-present ZhouXY
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.zhouxy.plusone.example;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 接口入参的 DTO 示例
 */
public class ExampleCommand {
    private Boolean boolProperty;
    private Integer intProperty;
    private Long longProperty;
    private Double doubleProperty;
    private String stringProperty;
    private LocalDateTime dateTimeProperty;
    private Foo objectProperty;
    private List<String> stringListProperty;
    private String[] stringArrayProperty;

    public ExampleCommand() {
    }

    public ExampleCommand(Boolean boolProperty, Integer intProperty, Long longProperty, Double doubleProperty,
            String stringProperty, LocalDateTime dateTimeProperty, Foo objectProperty,
            List<String> stringListProperty, String[] stringArrayProperty) {
        this.boolProperty = boolProperty;
        this.intProperty = intProperty;
        this.longProperty = longProperty;
        this.doubleProperty = doubleProperty;
        this.stringProperty = stringProperty;
        this.dateTimeProperty = dateTimeProperty;
        this.objectProperty = objectProperty;
        this.stringListProperty = stringListProperty;
        this.stringArrayProperty = stringArrayProperty;
    }

    public Boolean getBoolProperty() {
        return boolProperty;
    }

    public void setBoolProperty(Boolean boolProperty) {
        this.boolProperty = boolProperty;
    }

    public Integer getIntProperty() {
        return intProperty;
    }

    public void setIntProperty(Integer intProperty) {
        this.intProperty = intProperty;
    }

    public Long getLongProperty() {
        return longProperty;
    }

    public void setLongProperty(Long longProperty) {
        this.longProperty = longProperty;
    }

    public Double getDoubleProperty() {
        return doubleProperty;
    }

    public void setDoubleProperty(Double doubleProperty) {
        this.doubleProperty = doubleProperty;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public LocalDateTime getDateTimeProperty() {
        return dateTimeProperty;
    }

    public void setDateTimeProperty(LocalDateTime dateTimeProperty) {
        this.dateTimeProperty = dateTimeProperty;
    }

    public Foo getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(Foo objectProperty) {
        this.objectProperty = objectProperty;
    }

    public List<String> getStringListProperty() {
        return stringListProperty;
    }

    public void setStringListProperty(List<String> stringListProperty) {
        this.stringListProperty = stringListProperty;
    }

    public String[] getStringArrayProperty() {
        return stringArrayProperty;
    }

    public void setStringArrayProperty(String[] stringArrayProperty) {
        this.stringArrayProperty = stringArrayProperty;
    }

    @Override
    public String toString() {
        return "ExampleCommand [boolProperty=" + boolProperty + ", intProperty=" + intProperty + ", longProperty="
                + longProperty + ", doubleProperty=" + doubleProperty + ", stringProperty=" + stringProperty
                + ", dateTimeProperty=" + dateTimeProperty + ", objectProperty=" + objectProperty
                + ", stringListProperty=" + stringListProperty + ", stringArrayProperty="
                + Arrays.toString(stringArrayProperty) + "]";
    }
}
