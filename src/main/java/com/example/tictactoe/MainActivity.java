package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int [] chessboard=new int[9];//逻辑棋盘用于记录棋盘双方哪些地方落子
    private boolean []isFull=new boolean[9];//记录落子位置
    private int turn=0;//用于判断当前哪一方在落子，0代表圆，1代表1
    //在后面的选择先手方法中，两个先手按钮的功能分别将turn初始化为0或者1，已达到选择先手的目的
    private Button[]bts=new Button[12];//将所有Button放入一个数组方便后面添加监听事件
    private Drawable circle, cross,white;//图标
    private int circle_score=0,cross_score=0;//记录双方胜利比分


    boolean judge(int loc){
        //通过下面三个判断函数来判断是否胜利
        if (judgeCol(loc)||judgeDiag(loc)||judgeRow(loc)){
            return true;
        }else
            return false;

    }
    /*
    一维数组棋盘逻辑摆放方式如下
    0  1  2
    3  4  5
    6  7  8
    通过对当前下棋的位置i进行取模操作可以获得和i同行最左边或者同列最上面的数字
    然后检测该数字的右侧或者下侧两个数字是否和该数字相等来判断行或者列的胜利
     */
    boolean judgeRow(int loc){
        int i=loc-loc%3;
        if (chessboard[i]==chessboard[i+1]&&chessboard[i]==chessboard[i+2]){
            return true;
        }
        return false;
    }

    boolean judgeCol(int loc){
        int i=loc%3;
        if (chessboard[i]==chessboard[i+3]&&chessboard[i]==chessboard[i+6]){
            return true;
        }else
            return false;
    }


    boolean judgeDiag(int loc){

        if (loc==0&&chessboard[loc+4]==chessboard[loc]&&chessboard[loc]==chessboard[loc+8]){
            return true;
        }else if (loc==6&&chessboard[loc-2]==chessboard[loc]&&chessboard[loc]==chessboard[loc-4]){
            return true;
        }else if (loc==2&&chessboard[loc+2]==chessboard[loc]&&chessboard[loc]==chessboard[loc+4]){
            return true;
        }else if (loc==8&&chessboard[loc-4]==chessboard[loc]&&chessboard[loc]==chessboard[loc-8]){
            return true;
        }else if (loc==4&&chessboard[loc-4]==chessboard[loc]&&chessboard[loc]==chessboard[loc+4]){
            return true;
        }else if (loc==4&&chessboard[loc-2]==chessboard[loc]&&chessboard[loc]==chessboard[loc+2]){
            return true;
        }else
            return false;

    }



    //重置游戏函数
    public void resetGame(){
    for (int i=0;i<9;i++){
        //将布尔棋盘和数组棋盘全部重置
        isFull[i]=false;
        chessboard[i]=0;
        //通过白色图片来覆盖掉已经该上button的圆或叉图片
        bts[i].setBackground(white);
    }
        //重置先手为圆
        turn=0;

        //使选择先手按钮重新可用
        findViewById(R.id.choosecross).setEnabled(true);
        findViewById(R.id.choosecircle).setEnabled(true);

        //显示上局结束后双方的比分
        TextView temp=(TextView)findViewById(R.id.circleScore);
        temp.setText(Integer.toString(circle_score));
        temp=(TextView)findViewById(R.id.crossScore);
        temp.setText(Integer.toString(cross_score));


    }

    //重置双方分数
    public void initialScore(){
        circle_score=0;
        cross_score=0;
        TextView temp=(TextView)findViewById(R.id.circleScore);
        temp.setText(Integer.toString(circle_score));
        temp=(TextView)findViewById(R.id.crossScore);
        temp.setText(Integer.toString(cross_score));

    }

    //游戏控件和逻辑数组初始化
    public void initial(){
        for (int i=0;i<9;i++){
            isFull[i]=false;
            chessboard[i]=0;
        }
        //将图中Button添加入数组，方便后面分别添加监听空间
        Button bt=(Button)findViewById(R.id.bt00);
        bts[0]=bt;
        bt=(Button)findViewById(R.id.bt01);
        bts[1]=bt;
        bt=(Button)findViewById(R.id.bt02);
        bts[2]=bt;
        bt=(Button)findViewById(R.id.bt10);
        bts[3]=bt;
        bt=(Button)findViewById(R.id.bt11);
        bts[4]=bt;
        bt=(Button)findViewById(R.id.bt12);
        bts[5]=bt;
        bt=(Button)findViewById(R.id.bt20);
        bts[6]=bt;
        bt=(Button)findViewById(R.id.bt21);
        bts[7]=bt;
        bt=(Button)findViewById(R.id.bt22);
        bts[8]=bt;
        bt=(Button)findViewById(R.id.choosecircle);
        bts[9]=bt;
        bt=(Button)findViewById(R.id.choosecross);
        bts[10]=bt;
        bt=(Button)findViewById(R.id.reset);
        bts[11]=bt;

        //获得图片
        Resources r = getResources();
        circle = r.getDrawable(R.drawable.circle1);
        cross = r.getDrawable(R.drawable.cross1);
        white=r.getDrawable(R.drawable.white);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        View.OnClickListener listener=new View.OnClickListener(){
            //@override

            public void onClick(View v){


                if (v.getId()==R.id.choosecircle){
                    //选择圆先手
                   turn=0;
                   findViewById(R.id.choosecross).setEnabled(false);
                   findViewById(R.id.choosecircle).setEnabled(false);

                }else if (v.getId()==R.id.choosecross){
                    //选择叉先手
                    turn=1;
                    findViewById(R.id.choosecross).setEnabled(false);
                    findViewById(R.id.choosecircle).setEnabled(false);

                }else if(v.getId()==R.id.reset){
                    //复盘
                    turn=0;
                    resetGame();
                    //使用灰色覆盖掉当前的指明当前属于哪一方的回合按钮
                    findViewById(R.id.cross_turn).setBackgroundColor(Color.rgb(200, 200, 200));
                    findViewById(R.id.circle_turn).setBackgroundColor(Color.rgb(200, 200, 200));


                } else if (v.getId()==R.id.initialButton){
                    //重置分数
                    initialScore();

                }else {
                    //说明选择了先手，选择后使先手按钮无效
                    findViewById(R.id.choosecross).setEnabled(false);
                    findViewById(R.id.choosecircle).setEnabled(false);
                }


                if (turn%2==0){
                    //圆
                    int i=0;
                    //找出属于哪一个Button
                    for (;i<12;i++){
                        if (v==bts[i]&&i<9) {
                            if (isFull[i]==false){
                                isFull[i]=true;
                                chessboard[i]=1;
                                turn++;
                                //设置图片
                                bts[i].setBackground(circle);
                                if (judge(i)){
                                    //判断胜负
                                    Toast.makeText(MainActivity.this,"圆胜利",Toast.LENGTH_LONG).show();
                                    //分数加1
                                    circle_score++;
                                    //重置游戏
                                    //resetGame();
                                    //初始双方先手为圆
                                    turn=0;

                                }else {
                                    //显示当前属于哪一方的回合
                                    findViewById(R.id.circle_turn).setBackgroundColor(Color.rgb(200, 200, 200));
                                    findViewById(R.id.cross_turn).setBackgroundColor(Color.rgb(213, 0, 0));
                                }
                            }
                            else
                                //说明当前想要落子的位置已经被占
                                Toast.makeText(MainActivity.this,"该位置已满请重试",Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                }else {
                    //叉

                    int i=0;
                    for (;i<12;i++){
                        if (v==bts[i]&&i<9) {
                            if (isFull[i]==false) {
                                isFull[i] = true;
                                chessboard[i]=2;
                                //bts[i].setText("2");
                                turn++;
                                //设置图片
                                bts[i].setBackground(cross);


                                if (judge(i)){
                                    //判断胜负
                                    Toast.makeText(MainActivity.this,"叉胜利",Toast.LENGTH_LONG).show();
                                    //分数加1
                                    cross_score++;
                                    //resetGame();
                                    turn=0;
                                }else {
                                    //显示当前属于哪一方的回合
                                    findViewById(R.id.cross_turn).setBackgroundColor(Color.rgb(200, 200, 200));
                                    findViewById(R.id.circle_turn).setBackgroundColor(Color.rgb(213, 0, 0));
                                }
                            }
                            else
                                //说明当前想要落子的位置已经被占
                                Toast.makeText(MainActivity.this,"该位置已满请重试",Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }

            }

        };
        //添加监听控件
        findViewById(R.id.initialButton).setOnClickListener(listener);

        for (int i=0;i<12;i++){
            bts[i].setOnClickListener(listener);
        }


    }

}
