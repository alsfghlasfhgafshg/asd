package sales.salesmen.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseStarRepository {

    @Select("select course_id from course_users where users_id=#{uid}")
    List<Long> allStarCoursesid(@Param("uid") long uid);

}
