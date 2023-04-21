package com.example.admin.mapper;

import com.example.admin.core.model.XxlJobRegistry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobRegistryMapper {

    List<Integer> findDead(@Param("timeout") int timeout,
						   @Param("nowTime") Date nowTime);

    int removeDead(@Param("ids") List<Integer> ids);

    List<XxlJobRegistry> findAll(@Param("timeout") int timeout,
								 @Param("nowTime") Date nowTime);

    int registryUpdate(@Param("registryGroup") String registryGroup,
					   @Param("registryKey") String registryKey,
					   @Param("registryValue") String registryValue,
					   @Param("updateTime") Date updateTime);

    int registrySave(@Param("registryGroup") String registryGroup,
					 @Param("registryKey") String registryKey,
					 @Param("registryValue") String registryValue,
					 @Param("updateTime") Date updateTime);

    int registryDelete(@Param("registryGroup") String registryGroup,
					   @Param("registryKey") String registryKey,
					   @Param("registryValue") String registryValue);

}
