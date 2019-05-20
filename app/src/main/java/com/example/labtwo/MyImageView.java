package com.example.labtwo;


import android.content.Context;
import android.util.AttributeSet;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {
    static public int allCount;

    private int value;
    private int row;
    private int col;

    public MyImageView(Context context, int row, int col, int value) {
        super(context);
        setImageResource(R.drawable.button);
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageRes() {
        switch (getValue()) {
            case -1: {
                setImageResource(R.drawable.bomb);
                break;
            }
            case 0: {
                setImageResource(R.drawable.funnyowl);
                break;
            }
            case 1: {
                setImageResource(R.drawable.numberone);
                break;
            }
            case 2: {
                setImageResource(R.drawable.numbertwo);
                break;
            }
            case 3: {
                setImageResource(R.drawable.numberthree);
                break;
            }
            case 4: {
                setImageResource(R.drawable.numberfour);
                break;
            }
            case 5: {
                setImageResource(R.drawable.numberfive);
                break;
            }
            case 6: {
                setImageResource(R.drawable.numbersix);
                break;
            }
            case 7: {
                setImageResource(R.drawable.numberseven);
                break;
            }
            case 8: {
                setImageResource(R.drawable.numbereight);
                break;
            }
        }
        setClickable(false);
        setLongClickable(false);
        if (getValue() != -1)
            allCount--;
    }
}
