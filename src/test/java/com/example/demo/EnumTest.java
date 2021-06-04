package com.example.demo;

import com.example.demo.entity.EnumMethodDemo;
import com.example.demo.entity.ErrorCode;
import com.example.demo.entity.Signal;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Enum
 *
 * @author ZhangJP
 * @date 2021/5/10
 */
@SpringBootTest
public class EnumTest {

    //    @Test
    public void enumSetTest() {
        // EnumSet的使用
        System.out.println("EnumSet展示");
        EnumSet<ErrorCode> errSet = EnumSet.allOf(ErrorCode.class);
        for (ErrorCode e : errSet) {
            System.out.println(e.name() + " : " + e.ordinal());
        }

    }

    //    @Test
    public void enumMapTest() {
        // EnumMap的使用
        System.out.println("EnumMap展示");
        EnumMap<Signal, String> errMap = new EnumMap(Signal.class);
        errMap.put(Signal.RED, "红灯");
        errMap.put(Signal.YELLOW, "黄灯");
        errMap.put(Signal.GREEN, "绿灯");
        for (Iterator<Map.Entry<Signal, String>> iter = errMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Signal, String> entry = iter.next();
            System.out.println(entry.getKey().name() + " : " + entry.getValue());
        }
    }

    //    @Test
    public void enumMethod() {
        System.out.println("=========== Print all Color ===========");
        for (EnumMethodDemo.Color c : EnumMethodDemo.Color.values()) {
            System.out.println(c + " ordinal: " + c.ordinal());
        }
        System.out.println("=========== Print all Size ===========");
        for (EnumMethodDemo.Size s : EnumMethodDemo.Size.values()) {
            System.out.println(s + " ordinal: " + s.ordinal());
        }

        EnumMethodDemo.Color green = EnumMethodDemo.Color.GREEN;
        System.out.println("green name(): " + green.name());
        System.out.println("green getDeclaringClass(): " + green.getDeclaringClass());
        System.out.println("green hashCode(): " + green.hashCode());
        System.out.println("green compareTo Color.GREEN: " + green.compareTo(EnumMethodDemo.Color.GREEN));
        System.out.println("green equals Color.GREEN: " + green.equals(EnumMethodDemo.Color.GREEN));
        System.out.println("green equals Size.MIDDLE: " + green.equals(EnumMethodDemo.Size.MIDDLE));
        System.out.println("green equals 1: " + green.equals(1));
        System.out.format("green == Color.BLUE: %b\n", green == EnumMethodDemo.Color.BLUE);
    }

}
