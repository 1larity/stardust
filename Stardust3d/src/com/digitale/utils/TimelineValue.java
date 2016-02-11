package com.digitale.utils;

/**
* Provides an API to work with timeline values and mutable objects.
*
* @author acoppes
*
*/
public interface TimelineValue<T> {

/**
* Adds a new KeyFrame to the TimelineValue.
*
* @throws IllegalArgumentException
* If the KeyFrame internal value doesn't match the expected size.
*/
void addKeyFrame(KeyFrame keyFrame);

/**
* Internally modifies the value to the specified time using the KeyFrames.
*
* @param time
* The time to use when calculating the value.
*/
void setTime(float time);

/**
* Modifies the object based on the TimelineValue internal values, there is no need of previous binding.
*
* @param object
* The object to be modified.
* @param time
* The time on the timeline.
*/
void setTime(T object, float time);

/**
* Binds an object to be modified on the setTime(..).
*
* @param object
* The mutable object to be modified.
*/
void setObject(T object);

/**
* Returns the current binded object.
*/
T getObject();

/**
* Returns how many key frames the timeline value has.
*/
int getKeyFramesCount();

/**
* Returns the corresponding KeyFrame.
*
* @param i
* The index of the KeyFrame.
*/
KeyFrame getKeyFrame(int i);

}