package com.example.kholt6406.frisbee_app.swipe.simplify;

/**
 * Created by jmeo1609 on 1/26/2017.
 */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.example.kholt6406.frisbee_app.swipe.SwipeResolver;

public class ResolverCopy implements SwipeResolver {

    @Override
    public void resolve(Array<Vector2> input, Array<Vector2> output) {
        output.clear();
        output.addAll(input);
    }

}