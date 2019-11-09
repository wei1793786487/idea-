package com.hqgml.web.servlte;


import com.hqgml.domain.*;
import com.hqgml.service.Impl.MeetingFileSerletimpl;
import com.hqgml.service.Impl.MeetingServiceImpl;
import com.hqgml.service.Impl.logServiceImpl;
import com.hqgml.service.MeetingFileServlet;
import com.hqgml.service.MeetingService;
import com.hqgml.service.logService;
import com.hqgml.utlis.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 现在瞌睡了吧，好了，不要再改下面的代码了，睡觉咯~~
 * <p>
 * 因为我也不知道我写了啥 好像是用来读取发送来的文件
 */
@WebServlet("/file")
public class FileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MeetingService ms = new MeetingServiceImpl();
        logService logService=new logServiceImpl();
        String filepath = request.getSession().getServletContext().getRealPath("/upload/");
        HttpSession session = request.getSession();
        //日志操作完成
//        不存在，因为这个是本来存在session域里面的，现在修改了 修改存入request域里面了
//        String meetid = (String) session.getAttribute("meetid");
        MeetingFileServlet mf = new MeetingFileSerletimpl();
        MeetingUers mt = new MeetingUers();
        try {
            File fl = new File(filepath);
            if (!fl.exists()) {
                //如果不存在 那么就创建
                fl.mkdir();
            }
            //解析对象 获取文件
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //返回对象
            //设置文件上传的缓存为20m
            factory.setSizeThreshold(1024 * 1024 * 20);
            try {
                //t设置最大参数 为100m如果超过100m报异常
                upload.setSizeMax(1024 * 1024 * 100);
            } catch (Exception e) {
                response.getWriter().write("<script>alert('文件大小不能超过100m');window.history.back(-1);</script>");
                return;
//                e.printStackTrace();
            }
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    if (fileItem.getSize() == 0) {
                        response.getWriter().write("<script>alert('文件为空');window.history.back(-1);</script>");
                        return;
                    } else {
                        //获取上传的文件名
                        String fileName = fileItem.getName();
                        //获取person的名字
                        String personName = StringUtils.GetPrefix(fileName);
                        if (personName.length() > 10) {
                            response.getWriter().write("<script>alert('啥名字,10多位');window.history.back(-1);</script>");
                            return;
                        }
                        //判断是不是后缀名
                        String suffix = StringUtils.getSuffix(fileName);
                        System.out.println("后缀名为" + suffix);
                        //咱也不知道它为啥报警告
                        if (!(suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("bmp") || suffix.equalsIgnoreCase("zip"))) {
                            response.getWriter().write("<script>alert('只支持 png ,jpg ,jpeg,bmp,zip文件');window.history.back(-1);</script>");
                            return;
                        }
                        //改变filename防止唯一 获取存储的文件名称
                        String SaveName = StringUtils.getUUIDFileName(fileName);
                        fileItem.write(new File(filepath, SaveName));
                        //删除临时文件
                        fileItem.delete();
                        mt.setPerson_name(personName);
                        mt.setPhoto_name(SaveName);
                    }
                } else {
                    //这里使用else的原因是因为 文件上传的表单是属于非键值对的 而非键值对的参数就是id的参数 没有其他的参数
                    //获取id的参数
                    String meeting_id = fileItem.getString();
                    //判断这个id的参数是不真的存在 防止用户傻逼 瞎改id的参数
                    if (meeting_id == null || meeting_id.equals("")) {
                        response.getWriter().write("<script>alert('该会议不存在');window.history.back(-1);</script>");
                    }
                    //存入
                    mt.setMid(meeting_id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('服务器内部参数出现异常,请稍后再试');window.history.back(-1);</script>");
        }

        //前台咱也不知道咋回事 只刷新 不打印;  原因在 写jsp页面的时候触发了点击提交按钮就会自动刷新的功能

        //这里的数据库获取的uid对应的是meet表的id；
        Meeting meeting = ms.FindMeetingById(mt.getMid());//通过metid获取mid对应的uuid 作为存入的库
        //获取这个存入的图片的位置
        String person_image = filepath + File.separator + mt.getPhoto_name();
        System.out.println("替换之后的图标为"+person_image);
        //把图片转化为base64
        String base64_image = Base64Utils.GetImageStr(person_image);
//        System.out.println(base64_image);
        //将图片传入腾讯云
        // 腾讯云不不能存入.号 所有需要替换。替换为_;方便以后获取person表的对应关系
        String personName = StringUtils.ReplaceString(mt.getPhoto_name(),".","_");
//        personName是储存在腾讯的唯一标识
        TencentString tencentString = TencentUtils.CreatePerson(meeting.getUuid(), mt.getPerson_name(), personName, base64_image);
        if (tencentString.getError() != null) {
         String  ErrorMessage=tencentString.getError();
         if (ErrorMessage.contains("ImageDecodeFailed")){
             response.getWriter().write("<script>alert('图片解码失败');window.location.href=document.referrer;</script>");
         }else if (ErrorMessage.contains("NoFaceInPhoto")){
             response.getWriter().write("<script>alert('该图片不包含人脸');window.location.href=document.referrer;</script>");
         }else {
             response.getWriter().write("<script>alert('出现未知错误');window.location.href=document.referrer;</script>");
         }
        }else {
            boolean b = mf.SetMeeting(mt);
            if (b) {
                 LogUtlis.setlog(session,request,"人脸添加");
                response.getWriter().write("<script>alert('上传成功');window.location.href=document.referrer;</script>");
            } else {
                LogUtlis.setlog(session,request,"人脸添加,但是失败了");
                response.getWriter().write("<script>alert('文件上传过程出现问题');window.history.back(-1);</script>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
