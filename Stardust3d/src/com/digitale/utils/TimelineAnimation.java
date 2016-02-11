package com.digitale.utils;

import com.digitale.utils.Animation;
import com.digitale.utils.AnimationImpl;

/**
* An implementation of the Animation interface using a time line to animate values.
*
* @author acoppes
*/
public class TimelineAnimation implements Animation {

private Timeline timeline;
private AnimationImpl animation;

public void setDuration(float duration) {
animation.setDuration(duration);
}

public float getDuration() {
return animation.getDuration();
}

public void setSpeed(float speed) {
animation.setSpeed(speed);
}

public void setAlternateDirection(boolean alternateDirection) {
animation.setAlternateDirection(alternateDirection);
}

public float getCurrentTime() {
return animation.getCurrentTime();
}

@Override
public PlayingDirection getPlayingDirection() {
return animation.getPlayingDirection();
}

public void setDelay(float delay) {
animation.setDelay(delay);
}

public float getDelay() {
return animation.getDelay();
}

public TimelineAnimation(Timeline timeline, float duration) {
this(timeline, duration, false);
}

public TimelineAnimation(Timeline timeline, float duration, boolean started) {
this(timeline, duration, started, false);
}

public TimelineAnimation(Timeline timeline, float duration, boolean started, boolean alternateDirection) {
this.timeline = timeline;
this.animation = new AnimationImpl(started, alternateDirection);
this.animation.setDuration(duration);
}

/**
* Returns true when the time line for the current iteration is finished, could be normal or reverse playing direction.
*/
protected boolean isIterationFinished() {
return animation.isIterationFinished();
}

public Timeline getTimeline() {
return timeline;
}

@Override
public void start() {
start(1);
}

@Override
public void start(int iterationCount) {
animation.start(iterationCount);
timeline.move(animation.getCurrentTime());
}

@Override
public void start(int iterationCount, boolean alternateDirection) {
animation.start(iterationCount, alternateDirection);
}

@Override
public void restart() {
animation.restart();
}

public void stop() {
animation.stop();
}

public void pause() {
animation.pause();
}

public void resume() {
animation.resume();
}

@Override
public int getIteration() {
return animation.getIteration();
}

@Override
public boolean isFinished() {
return animation.isFinished();
}

@Override
public boolean isStarted() {
return animation.isStarted();
}

public void update(float time) {
animation.update(time);
timeline.move(animation.getCurrentTime() - animation.getDelay());
}

public void nextIteration() {
animation.nextIteration();
}

public void switchDirection() {
animation.switchDirection();
}

@Override
public float getSpeed() {
return animation.getSpeed();
}

}