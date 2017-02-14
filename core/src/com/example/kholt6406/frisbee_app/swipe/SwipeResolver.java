package com.example.kholt6406.frisbee_app.swipe;

/**
 * Created by jmeo1609 on 1/26/2017.
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public interface SwipeResolver {

    /**
     * Simplifies and smoothes the input.
     *
     * @param input the input of the swipe event
     * @param output the output instance
     */
    public void resolve(Array<Vector2> input, Array<Vector2> output);
}