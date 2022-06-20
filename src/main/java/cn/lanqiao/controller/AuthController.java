package cn.lanqiao.controller;

import cn.hutool.core.map.MapUtil;
import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;

import cn.lanqiao.exception.CaptchaException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class AuthController extends BaseController {
   @Autowired
   private Producer producer;
   @Autowired
   private RedisTemplate redisTemplate;

   /**
    * 生成验证码
    * @param request
    * @param response
    * @return
    * @throws IOException
    */
   @GetMapping("/captcha")
   public JsonResult captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String code = producer.createText();
      BufferedImage image = producer.createImage(code);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "jpg", outputStream);
      BASE64Encoder encoder = new BASE64Encoder();
      String str = "data:image/jpeg;base64,";
      String base64Img = str + encoder.encode(outputStream.toByteArray());
      redisTemplate.opsForValue().set("code", code, 1,TimeUnit.MINUTES);
      return JsonResult.success(MapUtil.builder()
              .put("captchaImg", base64Img)
              .build());
   }

   /**
    * 检验验证澳门
    * @param captcha 验证码
    * @return
    */
   @GetMapping("/code")
   public boolean verifyCode(String captcha) {
      Object code = redisTemplate.opsForValue().get("code");
      System.out.println(code);
      return captcha.equals(code);
   }
}