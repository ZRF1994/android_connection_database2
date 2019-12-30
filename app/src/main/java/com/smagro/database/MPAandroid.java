package com.smagro.database;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Zhang Rufei
 * @version $V1$
 * @data 2019/11/21
 * @data 1106815482@qq.com
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * @近30天数据可视化
 */
public class MPAandroid {



    public static void xxx(LineChart lineChart){

       //这里我们定义一下我们界面的控件
        lineChart=initLineChart(lineChart); //这里调用方法初始化柱状图
        LineData lineData=setLinedate(); //这里调用方法初始化模拟数据
        lineChart.setData(lineData);//这里将模拟数据用于柱状图，在柱状图显示
        lineChart.invalidate();  //这里让柱状图在填充数据后刷新

        lineChart.setPinchZoom(true);

        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        Legend line=lineChart.getLegend();
        line.setTextSize(10f);
        lineChart.setBackgroundColor(Color.WHITE & 0x70FFFFFF);
    }


    //        lineChart.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        //        lineChart.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        //lineChart.setLineWidth(1f);//线条宽度
        //到这里基本完成了柱状图的初步使用，如果想要再深一步的学习MPAndroidChart的使用，请关注我，谢谢
        //下面进行一下测试，看是否能够成功显示，这里up主使用的是mumu模拟器
        //在cmd中使用adb connect 127.0.0.1:7555就可以将mumu模拟器和Android studio相连接
        //等待的过程。。。还是很烦躁
        //数据成功显示，谢谢大家的耐心观看。。。。。


    /**
     * 这个方法用于设置柱状图数据
     * @return
     */
    /**
     * 设置折线图
     * @return
     */


    public static LineData setLinedate(){
        List<Entry> xxx=new ArrayList<>();
        for(int i = 0;i < 31;i++){
            xxx.add(new BarEntry(i,new Random().nextInt(200)));
        }
        Log.e("ma","aaaaaaaa"+xxx);
        LineDataSet lineDataSet=new LineDataSet(xxx,"xxx");
        lineDataSet.setLineWidth(3.75f); // 线宽

        lineDataSet.setCircleSize(5f);// 显示的圆形大小
        lineDataSet.setColor(Color.RED);// 显示颜色
        lineDataSet.setCircleColor(Color.BLUE);// 圆形的颜色
        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setLabel("sxasasa");
        lineDataSet.setFormSize(10f);//图例大小
        LineData lineData=new LineData(lineDataSet);

        return  lineData;
    }

    /**
     * 这个方法用于初始化柱状图
     * @return
     */

    public static LineChart initLineChart(LineChart lineChart){
        lineChart.setDescription(null);
        XAxis xAxis = lineChart.getXAxis();  //获取柱状图的x轴
        YAxis yAxisLeft = lineChart.getAxisLeft();   //这里获取了柱状图左侧的y轴
        YAxis yAxis1Right = lineChart.getAxisRight();//这里获取了柱状图右侧的y轴
        setAxis(xAxis,yAxisLeft,yAxis1Right);   //这里调用方法设置柱状图的轴线
        return lineChart;        //这里返回初始化完成的柱状图实例


    }
    /**
     * 这个方法用于设置柱状图的X轴Y轴
     */
    public static void setAxis(XAxis xAxis,YAxis leftYaxis,YAxis rightYaxis){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //这里设置x轴在柱状图底部显示
        xAxis.setAxisLineWidth(1);  //这里设置x轴宽度
        xAxis.setAxisMinimum(0);    //这里设置x轴从0刻度开始绘画
        xAxis.setDrawAxisLine(true);//这里设置x轴显示轴线
        xAxis.setDrawGridLines(false);//这里设置x轴的表格线不显示
        xAxis.setEnabled(true);     //这里设置x轴显示

        leftYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        leftYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        leftYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        leftYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        leftYaxis.setEnabled(true);//这里设置左侧的y轴显示

        rightYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        rightYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        rightYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        rightYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        rightYaxis.setEnabled(false);//这里设置右侧的y轴不显示

    }





}
