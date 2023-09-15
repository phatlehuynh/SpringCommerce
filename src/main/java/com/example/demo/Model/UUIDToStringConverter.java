//package com.example.demo.Model;
//
//import jakarta.persistence.*;
////import javax.persistence.AttributeConverter;
////import javax.persistence.Converter;
//import java.util.UUID;
//
//@Converter
//public class UUIDToStringConverter implements AttributeConverter<UUID, String> {
//
//    @Override
//    public String convertToDatabaseColumn(UUID attribute) {
//        return (attribute != null) ? attribute.toString() : null;
//    }
//
//    @Override
//    public UUID convertToEntityAttribute(String dbData) {
//        return (dbData != null) ? UUID.fromString(dbData) : null;
//    }
//}
