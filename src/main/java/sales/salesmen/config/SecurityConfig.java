package sales.salesmen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secert_key}")
    private String KEY = "dewitt";

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    WxLoginFilter wxLoginFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //测试
        http.authorizeRequests().antMatchers("/test**","/myself/mystar","/course/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/test*").permitAll();

        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**",
                "/index", "/img/**", "/file/**", "/lib/**").permitAll();

        http.authorizeRequests().antMatchers("/login", "/register").permitAll();


        http.authorizeRequests().antMatchers("/", "/home/**", "/myself",
                "/productservice", "/serving", "/serving/**", "/course", "/search/**").permitAll();

        http.authorizeRequests().antMatchers("/admins/**").hasRole("ADMIN");


        http.authorizeRequests().antMatchers("/myself/myclient").hasRole("SALES");


        http.authorizeRequests().antMatchers("/**").hasRole("SALES");
        http.authorizeRequests().antMatchers("/**").hasRole("USER");

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/admins/**").hasRole("ADMIN");



        //基于 Form 表单登录验证
        http.formLogin().loginPage("/login").failureUrl("/login-error")
                .successHandler(loginSuccessHandler)
                .and().rememberMe().key(KEY)
                .and().exceptionHandling().accessDeniedPage("/403");
        //监控
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/actuator/**").hasIpAddress("127.0.0.1");
        http.authorizeRequests().antMatchers("/druid/**").hasIpAddress("127.0.0.1");

        //注销登录
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/myself").invalidateHttpSession(true);

        http.headers().frameOptions().sameOrigin();
        http.addFilterAfter(wxLoginFilter, UsernamePasswordAuthenticationFilter.class);

        //在测试和上传文件时禁用 csrf
        http.csrf().ignoringAntMatchers("/uploadimg", "/test**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}
