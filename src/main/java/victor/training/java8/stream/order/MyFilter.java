//package victor.training.java8.stream.order;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import java.io.IOException;
//
//public class MyFilter implements Filter {
//   @Override
//
//   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//      HttpServletRequest req = (HttpServletRequest) request;
//      String la_liber_nu_se_sterg = req.getHeader("la liber nu se sterg");
//
//
////      request.getReader().
//      HttpServletResponse res = (HttpServletResponse) response;
//      res.comm
//      chain.doFilter(copiedRequest, new HttpServletResponseWrapper(
//          res){
//         @Override
//         public ServletOutputStream getOutputStream() throws IOException {
//            return super.getOutputStream();
//         }
//      });
//
//
//
//      response.getOutputStream()
//
//      response.getWriter().println("Si eu");
//
//   }
//}
