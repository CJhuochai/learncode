package com.example.demo;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.DbUser;
import com.example.demo.entity.Deadlock;
import com.example.demo.entity.UserExport;
import com.example.demo.service.ServiceDemo;
import com.example.demo.spring.AsynTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
class LlmitingDemoApplicationTests {
    private static final String STRING="家长关系：{0},名字：{1},手机号：{2},邮箱：{3}";

    @Autowired
    private RedissonClient redissonClient;

    /*@Autowired
    private ISms smsService;*/

@Autowired
    private ServiceDemo serviceDemo;

    @Test
    public void asynTest() throws Exception {
        serviceDemo.bb();
    }

  /*  @Test
    public void testsms(){
        final SmsPro smsPro = smsService.get();
        log.info("smsp:{},send:{}",smsPro,this.smsService.send());
    }*/

    @Test
    public void ds(){
        final String format = MessageFormat.format(STRING, "父亲", "陈健", "13098857796", "2452019836@qq.com");
        final String format1 = MessageFormat.format(STRING, "父亲", "陈健", "13098857796", "2452019836@qq.com");
        final String collect = Arrays.asList(format, format1).stream().collect(Collectors.joining("\n"));
        System.out.println(collect);
    }

    @Test
    void contextLoads() throws InterruptedException {
        RLock rLock = redissonClient.getLock("abc");
        boolean lock = rLock.tryLock(10, TimeUnit.SECONDS);
        System.out.println("thread1 lock."+ lock);
        final Thread thread = new Thread(() -> {
            System.out.println("thread2 start.");
            RLock rLock1 = redissonClient.getLock("abc");
            boolean lock1 = false;
            try {
                lock1 = rLock1.tryLock(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 lock:" + lock1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lock1){
                rLock1.unlock();
                System.out.println("thread2 unlock.");
            }

        });
        thread.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rLock.unlock();
        System.out.println("thread1 unlock.");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //设置读取值
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("a");
        RBucket<String> kv2 = redissonClient.getBucket("1");
        System.out.println(kv2.get());
    }
   @Test
   public void test1(){
       System.out.println("你好");
   }

   @Autowired
   private WebApplicationContext webApplicationContext;


    @Test
    public void listener(){
        this.serviceDemo.a();
    }

    @Test
    public void dateTest(){
        LocalDate localDate = LocalDate.now();
        log.info("localDate-now:{}",localDate);
        log.info("localDate-year:{}",localDate.getYear());
        log.info("localDate-month:{}",localDate.getMonth().name());
        log.info("localDate-monthDay:{}",localDate.getDayOfMonth());
        log.info("localDate-dayWeek:{}",localDate.getDayOfWeek().name());
        log.info("localDate-dayYear:{}",localDate.getDayOfYear());
    }

    @Test
    public void preTest() {
        String token1 = "68eb5da2-7f74-4a54-872b-eda284163045:c2269b3c-c3b8-49a9-9549-738c4f192912";
        final String s2 = Base64Utils.encodeToString(token1.getBytes());
        System.out.println(s2);
    }


    private List<Boolean> pre(List<String> a, Predicate<String> predicate){
        List<Boolean> rs = new ArrayList<>(a.size());
        for (String b: a) {
            if (predicate.test(b)){
                rs.add(true);
            }else {
                rs.add(false);
            }
        }
        return rs;
    }
    @Test
    public void biFunctionTest(){
        final List<String> list = Arrays.asList("aaa", "bb", "vv", "dd");
        final Integer bb = this.getStr("bb", list, (a, b) -> {
            if (b.contains(a)) {
                return 1;
            } else {
                return 0;
            }
        });
        System.out.println(bb);
    }
    private Integer getStr(String target, List<String> datas, BiFunction<String,List<String>,Integer> biFunction){
        return biFunction.apply(target,datas) + 1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<UserExport> users = new ArrayList<>();
        for (int i = 0;i<20;i++){
            UserExport user = new UserExport();
            user.setId("chenjian:" + i);
            user.setAge(10 + i);
            user.setName("陈健"+i);
            user.setDate(new Date());
            users.add(user);
        }
        FileOutputStream outputStream = new FileOutputStream(new File("F:/userInfo.xlsx"));
        EasyExcel.write(outputStream,UserExport.class).sheet("用户信息").doWrite(users);
        String msg = "【正邦奖】：验证码：{0}，验证码15分钟内有效，此验证码仅用于正邦奖网站的验证，请勿泄露给他人";
        final String format = MessageFormat.format(msg, "1234");
        System.out.println(format);
    }

    @Test
    public void importExcel() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File("F:/userInfo.xlsx"));
        final List<UserExport> objects = EasyExcel.read(inputStream).head(UserExport.class).sheet().doReadSync();
        System.out.println(JSON.toJSONString(objects));
    }

    @Test
    public void jsoup1() throws IOException {
        final Document document = Jsoup.connect("http://www.baidu.com/").data("wd","你好").get();
        System.out.println(document);
    }

    @Test
    public void founction1() throws IOException {
        String a= "3";
        final String content1 = this.content(() -> a);
        System.out.println(content1);
    }

    public String content(Supplier<String> supplier){
        StringBuilder builder = new StringBuilder();
        builder.append("<br>");
        builder.append("<br>");
        builder.append("<img src='https://bgbasis-pre.oss-cn-shenzhen.aliyuncs.com/basis-mini/basis-mini/BG-CJ.jpg'>");
        return supplier.get() + builder.toString();
    }
    @Test
    public void a(){
        final Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(Deadlock.class);
        for (Method allDeclaredMethod : allDeclaredMethods) {
            System.out.println(allDeclaredMethod.getName());
        }
    }

}
