package com.example.MobileDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTemplateQueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object getMobileData(String table){
        String sql = "SELECT * FROM "+table;
        List<MobileData> mobileData = new ArrayList<>();
        try {
            jdbcTemplate.query(sql, rs -> {
                        MobileData m = new MobileData(
                                rs.getLong("id"),
                                rs.getString("manufacturer"),
                                rs.getString("model"),
                                rs.getString("part"),
                                rs.getInt("quantity"),
                                rs.getString("description")
                        );
                        mobileData.add(m);
                    }
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new DefaultResponse("Cannot retrieve data from "+table,false)
            );
        }
        return mobileData;
    }

    public Object insertMobileData(MobileData data, String table){
        String sql = "INSERT INTO " + table + "( " +
                "manufacturer," +
                "model," +
                "part," +
                "quantity," +
                "description) VALUES ('" +
                data.getManufacturer() + "', '"+
                data.getModel() + "', '" +
                data.getPart() + "', '" +
                data.getQuantity() + "', '" +
                data.getDescription() + "')";

        try{
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse(e.getMessage(),false) );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new DefaultResponse("Data inserted successfully",true) );
    }

    public Object updateRecordById(String tableName, MobileData data){
        String sql = "UPDATE "+ tableName +
                " SET manufacturer ='"+ data.getManufacturer()+"' ,"+
                    "model = '"+data.getModel()+"' ,"+
                    "part = '"+data.getPart()+"' ,"+
                    "quantity = '"+data.getQuantity()+"' ,"+
                    "description = '"+data.getDescription()+"' "+
                    "WHERE id = " + data.getId();

        try{
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse(e.getMessage(),false) );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new DefaultResponse("Successfully updated ID: "+data.getId(),true) );
    }

    public Object deleteRecordById(String tableName, Long id){
        String sql = "DELETE FROM "+tableName+ " WHERE id = "+id;

        try{
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new DefaultResponse(e.getMessage(),false) );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new DefaultResponse("Successfully deleted ID: "+id,true) );
    }


}
