# s2ui-domain-class-issue
A simple demonstration of the issue found when using the s2ui plugin in a grails multi-project with domain classes in a plugin subproject

-----

Our team has a new Grails 3.3.9 multi-project.  This issue manifests almost exactly like #56 but the work-around suggested there did not work.  Ultimately, copying the Domain classes into the myweb subproject fixed the issue.  We want our Domain classes, including those for Spring Security (SecUser and SecRole) to reside in the shared plugin.

### Task List

- [x] Steps to reproduce provided
- [x] Stacktrace (if present) provided
- [x] Example that reproduces the problem uploaded to Github
- [x] Full description of the issue provided (see below)

### Steps to Reproduce

1. Setup a Grails multi-project as detailed in this article: http://www.databaseapplications.com.au/grails-multi-app.jsp
2. Add s2ui to the myweb subproject
       `compile 'org.grails.plugins:spring-security-ui:3.1.2'`
3. Generate Controllers and crud gsps with s2ui-override command line:
    1. `grails s2ui-override auth`
    2. `grails s2ui-override layout`
    3. `grails s2ui-override role com.example` (replace existing controller from earlier generate-all)
    4. `grails s2ui-override user com.example` (replace existing controller from earlier generate-all)
4. `grails run-app`
5. Login to the app with user `admin` and password `strongPassword`.
6. Navigate to `/role/create` or `/user/create`

### Expected Behaviour

Create Role and Create User pages should load without error

### Actual Behaviour
```
Error 500: Internal Server Error
URI
/role/create
Class
org.codehaus.groovy.runtime.powerassert.PowerAssertionError
Message
Request processing failed; nested exception is org.grails.gsp.GroovyPagesException: Error processing GroovyPageView: [views/role/create.gsp:21] Error executing tag <s2ui:formContainer>: assert bean | null
Caused by
assert bean | null
```
#### Full trace from grails:
```
    Line | Method
->>  473 | createGroovyPageException    in /Users/dgoodman/git/atomic-mole/web-admin/grails-app/views/role/create.gsp
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Caused by GrailsTagException: [views/role/create.gsp:21] Error executing tag <s2ui:formContainer>: assert bean
       |
       null
->>   21 | throwRootCause               in views/role/create.gsp
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Caused by PowerAssertionError: assert bean
       |
       null
->>  312 | doCall                       in grails.plugin.springsecurity.ui.SecurityUiTagLib$_closure10
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
|    446 | invokeTagLibClosure          in org.grails.gsp.GroovyPage
|    364 | invokeTag . . . . . . . . .  in     ''
|     39 | doCall                       in Users_dgoodman_git_atomic_mole_web_admin_grails_app_views_role_create_gsp$_run_closure2
|    200 | executeClosure . . . . . . . in org.grails.taglib.TagBodyClosure
|    102 | captureClosureOutput         in     ''
|    213 | call . . . . . . . . . . . . in     ''
|     48 | captureTagContent            in org.grails.plugins.web.taglib.SitemeshTagLib
|    156 | doCall . . . . . . . . . . . in org.grails.plugins.web.taglib.SitemeshTagLib$_closure3
|    446 | invokeTagLibClosure          in org.grails.gsp.GroovyPage
|    364 | invokeTag . . . . . . . . .  in     ''
|     42 | run                          in Users_dgoodman_git_atomic_mole_web_admin_grails_app_views_role_create_gsp
|    162 | doWriteTo . . . . . . . . .  in org.grails.gsp.GroovyPageWritable
|     82 | writeTo                      in     ''
|     76 | renderTemplate . . . . . . . in org.grails.web.servlet.view.GroovyPageView
|     71 | renderWithinGrailsWebRequest in org.grails.web.servlet.view.AbstractGrailsView
|     55 | renderMergedOutputModel . .  in     ''
|    304 | render                       in org.springframework.web.servlet.view.AbstractView
|    150 | renderInnerView . . . . . .  in org.grails.web.sitemesh.GrailsLayoutView
|    128 | obtainContent                in     ''
|     63 | renderTemplate . . . . . . . in     ''
|     71 | renderWithinGrailsWebRequest in org.grails.web.servlet.view.AbstractGrailsView
|     55 | renderMergedOutputModel . .  in     ''
|    304 | render                       in org.springframework.web.servlet.view.AbstractView
|   1286 | render . . . . . . . . . . . in org.springframework.web.servlet.DispatcherServlet
|   1041 | processDispatchResult        in     ''
|    984 | doDispatch . . . . . . . . . in     ''
|    901 | doService                    in     ''
|    970 | processRequest . . . . . . . in org.springframework.web.servlet.FrameworkServlet
|    861 | doGet                        in     ''
|    846 | service . . . . . . . . . .  in     ''
|     55 | doFilterInternal             in org.springframework.boot.web.filter.ApplicationContextHeaderFilter
|    317 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    127 | invoke                       in org.springframework.security.web.access.intercept.FilterSecurityInterceptor
|     91 | doFilter . . . . . . . . . . in     ''
|    331 | doFilter                     in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    114 | doFilter . . . . . . . . . . in org.springframework.security.web.access.ExceptionTranslationFilter
|     64 | doFilter                     in grails.plugin.springsecurity.web.UpdateRequestContextHolderExceptionTranslationFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|     54 | doFilter                     in grails.plugin.springsecurity.web.filter.GrailsAnonymousAuthenticationFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    158 | doFilter                     in org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    170 | doFilter                     in org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    200 | doFilter                     in org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|     64 | doFilter                     in grails.plugin.springsecurity.web.authentication.logout.MutableLogoutFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    105 | doFilter                     in org.springframework.security.web.context.SecurityContextPersistenceFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|     58 | doFilter                     in grails.plugin.springsecurity.web.SecurityRequestHolderFilter
|    331 | doFilter . . . . . . . . . . in org.springframework.security.web.FilterChainProxy$VirtualFilterChain
|    214 | doFilterInternal             in org.springframework.security.web.FilterChainProxy
|    177 | doFilter . . . . . . . . . . in     ''
|     77 | doFilterInternal             in org.grails.web.servlet.mvc.GrailsWebRequestFilter
|     67 | doFilterInternal . . . . . . in org.grails.web.filters.HiddenHttpMethodFilter
|   1142 | runWorker                    in java.util.concurrent.ThreadPoolExecutor
|    617 | run . . . . . . . . . . . .  in java.util.concurrent.ThreadPoolExecutor$Worker
^    745 | run                          in java.lang.Thread
```
### Environment Information

- **Operating System**: Mac OS 10.11.3
- **GORM Version:** 6.1.11.RELEASE
- **Grails Version (if using Grails):** 3.3.9
- **Groovy Version:** 2.4.15
- **JDK Version:** 1.8.0_11

### Example Application

- https://github.com/dgoodman-idea/s2ui-domain-class-issue
