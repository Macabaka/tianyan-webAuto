package com.tianyancha.patent.util;
import com.tianyancha.patent.entity.Patent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Johnny
 * @Date: 2020/4/18 14:55
 * @Description:
 */
public class Spider {
    public static WebDriver driver;
    public static List<Patent> patentList = new ArrayList<>();

    public static void main(String[] args) {
        getAll();
    }

    public static void getAll(){
        //启动selenium,并且指定浏览器插件
        System.setProperty("webdriver.chrome.driver","E:\\study\\java-jar-jdk\\chromedriver.exe");
        driver = new ChromeDriver();
        //打开网页
        driver.get("https://www.tianyancha.com/company/2342419528");
        try {
            Thread.sleep(20000);
        }catch (Exception e){
        }
        //窗口最大化
        driver.manage().window().maximize();

        //获取前10页的数据
        for(int topNum=1;topNum<=10;topNum++){
            getSleep(3000);
            getTopMessage(topNum);
            getSleep(3000);
        }

        //获取10页后数据
//        for(int realPage =11;realPage<=20;realPage++){
//            int nowIndex = realPage;
//            nowIndex-=10;
//            getMoreMessage(nowIndex);
//            getSleep(3000);
//        }
        //结束
        getSleep(10000);
        driver.close();
    }

    /**
     * 获取前10页数据方法
     * @param Nowpage
     */
    public static void getTopMessage(int Nowpage) {
        for(int i=1;i<=10;i++){
            //切换1-10分页
            getChangePage(Nowpage);
            //获取每一页数据
            getContent(i);
            getSleep(1000);
        }

    }

    /**
     * 分页切换
     * @param changePage
     */
    public static void getChangePage(int changePage) {
        getSleep(3000);
        //切换分页主要方法
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement page=driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/div/ul/li["+changePage+"]/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(page).click().build().perform();
        getSleep(3000);
    }

    /**
     * 获取10页后的数据内容
     * @param nowIndex
     */
    public static void getMoreMessage(int nowIndex ) {
        //备份nowIndex,下文循环中页面回退之后要重新跳转
        int copyNum=nowIndex;
        //在跳到意向页面时，先进行多余页面跳转
        getChangePage(10);
        //判断跳的分页数，是不是在视线范围内，如果不是，就要通过循环来把目标分页引出
        while(nowIndex>5){
            getChangePage(12);
            getSleep(2000);
            nowIndex-=5;
        }
        //最后一步————跳转到目标页面
        getChangePage(nowIndex+7);
        getSleep(2000);
        for(int i=1;i<=10;i++){
            getContent(i);
            getSleep(1000);
            //如果已经获取完分页中的最后一条内容，就不应再跳回上一级界面，直接待系统跳回初始页
            if(i!=10){
                getChangePage(10);
                while(copyNum>5){
                    getChangePage(12);
                    getSleep(2000);
                    copyNum-=5;
                }
                getChangePage(copyNum+7);
            }
        }
    }

    /**
     * 纯页面数据获取，并且存到list集合中
     */
    public static void getContent(int i){
        //切换页面后的数据缓冲时间
        getSleep(1000);
        WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]"));
        //获取发布日期
        String date = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[2]/span")).getText();
        //获取专利名称
        String name = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[3]/span")).getText();
        //获取申请号
        String requestNum = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[4]/span")).getText();
        //获取申请公布号
        String publicNum = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[5]/span")).getText();
        //获取发明类型
        String type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[6]/span")).getText();
        WebElement button= driver.findElement(By.xpath("/html/body/div[2]/div/div/div[5]/div[1]/div/div[2]/div[6]/div[3]/div[2]/table/tbody/tr["+i+"]/td[7]/a"));
        driver.get(button.getAttribute("href"));
        //获取发明人信息
        String author = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/table/tbody/tr[5]/td[2]")).getText();
        //        System.out.println(author);
        patentList.add(Patent.builder().name(name).author(author).publicNum(publicNum).requestNum(requestNum).type(type).date(date).build());
        getSleep(1000);
        driver.navigate().back();
    }


    public static void getSleep(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
