package sales.salesmen.repository;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserCourseStarRespository {
    @Select("select count(*) from course_users where course_id=#{course_id} and users_id=#{user_id}")
    int isStarCourse(@Param("course_id") long course_id, @Param("user_id") long user_id);

    @Insert("insert into course_users(course_id,users_id) values(#{course_id},#{user_id})")
    int starCourse(@Param("course_id") long course_id, @Param("user_id") long user_id);

    @Delete("delete from course_users where course_id=#{course_id} and users_id=#{user_id}")
    int disStarCourse(@Param("course_id") long course_id, @Param("user_id") long user_id);

}

