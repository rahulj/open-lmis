package org.openlmis.rnr.repository.mapper;


import org.apache.ibatis.annotations.*;
import org.openlmis.rnr.domain.RegimenLineItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenLineItemMapper {

  @Insert({"INSERT INTO regimen_line_items(code, name, regimenDisplayOrder, regimenCategory, regimenCategoryDisplayOrder, rnrId) values " +
    "(#{regimen.code}, #{regimen.name}, #{regimen.displayOrder}, #{regimen.category.name}, #{regimen.category.displayOrder}, #{rnrId})"})
  @Options(useGeneratedKeys = true)
  public void insert(RegimenLineItem regimenLineItem);

  @Select("SELECT * FROM regimen_line_items WHERE rnrId = #{rnrId}")
  @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "regimen.code", column = "code"),
    @Result(property = "regimen.name", column = "name"),
    @Result(property = "regimen.displayOrder", column = "regimenDisplayOrder"),
    @Result(property = "regimen.category.name", column = "regimenCategory"),
    @Result(property = "regimen.category.displayOrder", column = "regimenCategoryDisplayOrder"),
  })
  public List<RegimenLineItem> getRegimenLineItemsByRnrId(Long rnrId);
}