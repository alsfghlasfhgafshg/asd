package sales.salesmen.service;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.CCatalog;
import sales.salesmen.entity.CCatalog2;
import sales.salesmen.entity.Course;
import sales.salesmen.entity.User;
import sales.salesmen.repository.CCatalog2Repository;
import sales.salesmen.repository.CCatalogRepository;
import sales.salesmen.repository.CourseRepository;
import sales.salesmen.repository.UserCourseStarRespository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class CourseService {


    @Value("${pageSize}")
    int pageSize;

    @Autowired
    FileService fileService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CCatalogRepository cCatalogRepository;

    @Autowired
    CCatalog2Repository cCatalog2Repository;

    @Autowired
    UserCourseStarRespository userCourseStarRespository;

    public Course saveOrUpdate(Course course) {
        return null;
    }


    public String uploadsrc(int catalogid, MultipartFile src) {
        String srcuri;
        CCatalog cCatalog = cCatalogRepository.getOne(catalogid);
        if (cCatalog.getName().equals("视频课程")) {
            srcuri = fileService.uploadVideo(src);
        } else if (cCatalog.getName().equals("音频课程")) {
            srcuri = fileService.uploadAudio(src);
        } else if (cCatalog.getName().equals("文档课程")) {
            srcuri = fileService.uploadDoc(src);
        } else {
            srcuri = fileService.uploadOther(src);
        }
        return srcuri;
    }

    @Transactional
    public boolean saveOrUpdate(Long courseid, int catalogid, int catalog2id, String title, String teacher,
                                MultipartFile pic, MultipartFile src, String summary) {
        CCatalog cCatalog = cCatalogRepository.getOne(catalogid);

        if (courseid == null) {
            Course course;
            String picuri = fileService.uploadImage(pic);
            String srcuri = uploadsrc(catalogid, src);

            CCatalog2 cCatalog2 = cCatalog2Repository.getOne(catalog2id);
            course = new Course(courseid, title, picuri, teacher, summary, 0, 0, srcuri, cCatalog, cCatalog2);
            courseRepository.save(course);
            return true;
        } else {
            try {
                Course course = courseRepository.getOne(courseid);

                course.setTitle(title);
                if (pic != null) {
                    String picuri = fileService.uploadImage(pic);
                    course.setPictureuri(picuri);
                }
                if (src != null) {
                    String srcuri = uploadsrc(catalogid, src);
                    course.setSrcuri(srcuri);
                }
                course.setTeacher(teacher);
                course.setSummary(summary);
                course.setcCatalog(cCatalogRepository.getOne(catalogid));
                course.setcCatalog2(cCatalog2Repository.getOne(catalog2id));
                courseRepository.save(course);
                return true;

            } catch (Exception e) {
                return false;
            }
        }
    }

    public List<Course> getAllCourseByccatalog12(int ccatalog, int ccatalog2, int page) {
        PageRequest pageRequest = PageRequest.of(page, this.pageSize);
        return courseRepository.findAllByCCatalogAndAndCCatalog2(pageRequest, cCatalogRepository.findById(ccatalog).get(), cCatalog2Repository.findById(ccatalog2).get()).getContent();
    }

    public boolean deleteCourse(Long courseid) {
        courseRepository.deleteById(courseid);
        return true;
    }


    public Course getCourseById(long id) {
        return courseRepository.findById(id).get();
    }

    public List<CCatalog> getAllCCatalog() {
        return cCatalogRepository.findAll();
    }

    public List<CCatalog2> getAllCCatalog2() {
        return cCatalog2Repository.findAll();
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    public boolean isStar(long courseid, long userid) {
        if (userCourseStarRespository.isStarCourse(courseid, userid) == 0) {
            return false;
        }
        return true;
    }

    public boolean statCourse(long courseid, long userid) {
        userCourseStarRespository.starCourse(courseid, userid);
        return true;
    }

    public boolean disStarCourse(long courseid, long userid) {
        userCourseStarRespository.disStarCourse(courseid, userid);
        return true;
    }
}
