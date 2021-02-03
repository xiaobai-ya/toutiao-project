package com.heima.admin;

import com.heima.admin.AdminApplication;
import com.heima.common.aliyun.GreeTextScan;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.fastdfs.FastDFSClient;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @create 2021/2/3 23:37
 */
@SpringBootTest(classes = AdminApplication.class)
@RunWith(SpringRunner.class)
public class AliyunApiTest {

    @Autowired
    private GreeTextScan greeTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 测试文本检测
     * @throws Exception
     */
    @Test
    public void testScanText() throws Exception{
        Map map = greeTextScan.greeTextScan("这是要检测的文本,大麻!");

        System.out.println(map);

    }

    /**
     * 测试图片扫描
     * @throws Exception
     */
    @Test
    public void testScanImage()throws Exception{

        byte[] download = fastDFSClient.download("group1","M00/00/00/wKjIgmAaxeSAFcDSAAAs_3I7l38206.jpg");  //注意路径,不能以/开头,否则报错参数错误

        List<byte[]> imagesList =new ArrayList<>();
        imagesList.add(download);


        Map map = greenImageScan.imageScan(imagesList);

        System.out.println(map);

    }
}
