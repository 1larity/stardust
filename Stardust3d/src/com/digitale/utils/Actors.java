/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.FlickScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.digitale.mygdxgame.Stardust3d;
import com.digitale.screens.GameLoop;

public class Actors {

private static Color windowColor;

public static Actor topToast(final String text, final float time, final Skin skin) {
final Window window = new Window("", skin);
final Color windowColor = new Color(1, 0, 0, 1);
window.setMovable(false);

window.defaults().spaceBottom(5);

Label toastLabel = new Label(text, skin);
toastLabel.setAlignment(Align.LEFT);
toastLabel.setWrap(true);

window.row().fillX().expandX();
window.add(toastLabel).fillX().padLeft(10);

window.invalidate();

window.width =Gdx.app.getGraphics().getWidth() * 0.95f;
window.height = toastLabel.getTextBounds().height + 20 + window.getStyle().titleFont.getLineHeight();

window.x = Gdx.app.getGraphics().getWidth() * 0.5f - window.width * 0.5f;

float outsideY = Gdx.app.getGraphics().getHeight() + window.height;
float insideY =  Gdx.app.getGraphics().getHeight() - window.height + window.getStyle().titleFont.getLineHeight();

window.y = outsideY;

final TimelineAnimation timelineAnimation = Builders.animation( //
Builders.timeline() //
.value(Builders.timelineValue(window, Scene2dConverters.actorPositionTypeConverter) //
.keyFrame(0f, new float[] { window.x, outsideY }, //
InterpolationFunctions.linear(), InterpolationFunctions.easeIn()) //
.keyFrame(1f, new float[] { window.x, insideY }) //
.keyFrame(4f, new float[] { window.x, insideY }, //
InterpolationFunctions.linear(), InterpolationFunctions.easeOut()) //
.keyFrame(5f, new float[] { window.x, outsideY }) //
) //
) //
.started(true) //
.delay(0f) //
.speed(5f / time) //
.build();

window.action(new ActionAdapter(){
@Override
public void act(float delta) {
timelineAnimation.update(delta);

if (timelineAnimation.isFinished()) {
window.getStage().removeActor(window);
}
}
});

return window;
}
public static Actor bottomToast(final String text, final float time,  Skin skin) {
	skin = new Skin(Gdx.files.internal("data/dialogskin.json"),
			Gdx.files.internal("data/dialogskin.png"));
	final Window window = new Window("", skin);

	window.setMovable(false);

	window.defaults().spaceBottom(5);

	Label toastLabel = new Label(text, skin);
	toastLabel.setAlignment(Align.LEFT);
	toastLabel.setWrap(true);
	window.row().fillX().expandX();
	window.add(toastLabel).fillX().padLeft(10);

	window.invalidate();

	window.width = Gdx.graphics.getWidth() * 0.5f;
	window.height = toastLabel.getTextBounds().height *5 + window.getStyle().titleFont.getLineHeight();

	window.x = Gdx.graphics.getWidth() * 0.5f - window.width * 0.5f;

	float outsideY = 0 - window.height;
	float insideY = -20 +window.height + window.getStyle().titleFont.getLineHeight();

	window.y = outsideY;

	final TimelineAnimation timelineAnimation = Builders.animation( //
	Builders.timeline() //
	.value(Builders.timelineValue(window, Scene2dConverters.actorPositionTypeConverter) //
	.keyFrame(0f, new float[] { window.x, outsideY }, //
	InterpolationFunctions.linear(), InterpolationFunctions.easeIn()) //
	.keyFrame(.5f, new float[] { window.x, insideY }) //
	.keyFrame(4f, new float[] { window.x, insideY }, //
	InterpolationFunctions.linear(), InterpolationFunctions.easeOut()) //
	.keyFrame(4.5f, new float[] { window.x, outsideY }) //
	) //
	) //
	.started(true) //
	.delay(0f) //
	.speed(5f / time) //
	.build();

	window.action(new ActionAdapter(){
	@Override
	public void act(float delta) {
	timelineAnimation.update(delta);

	if (timelineAnimation.isFinished()) {
	window.getStage().removeActor(window);
	}
	}
	});

	return window;
	}
public static interface DialogListener {

void optionSelected(int option);

}

public static Actor twoOptionsDialog(String texts, final DialogListener dialogListener, String titleText, String firstOption, String secondOption, Skin skin) {
Window window = new Window(titleText, skin);

window.setMovable(false);

TextButton firstOptionButton = new TextButton(firstOption, skin);
TextButton secondOptionButton = new TextButton(secondOption, skin);

firstOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(0);
}
});

secondOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(1);
}
});

window.defaults().spaceBottom(5);
window.row().fill().expandX();

//for (int i = 0; i < texts.length; i++) {
window.row().padLeft(10);
Label label = new Label(texts, skin);
label.setWrap(true);
window.add(label).align(Align.LEFT).colspan(2).fillX();
//}

window.row().fill().expandX();
window.add(firstOptionButton).align(Align.CENTER).padLeft(5).padRight(5).expandX();
window.add(secondOptionButton).align(Align.CENTER).padLeft(5).padRight(5).expandX();

FlickScrollPane scrollPane = new FlickScrollPane(window);

//scrollPane.width = GameLoop.camera.viewportWidth * 0.5f;
scrollPane.width =  Gdx.graphics.getWidth() * 0.5f;
scrollPane.height = Gdx.graphics.getHeight() * 0.6f;
scrollPane.x =  Gdx.graphics.getWidth() * 0.5f - scrollPane.width * 0.5f;
scrollPane.y =  Gdx.graphics.getHeight() * 0.6f - scrollPane.height * 0.6f;

return scrollPane;
}

public static Actor threeOptionsDialog(String texts, final DialogListener dialogListener, String titleText, String firstOption, String secondOption, String thirdOption, Skin skin) {
Window window = new Window(titleText, skin);

window.setMovable(true);

TextButton firstOptionButton = new TextButton(firstOption, skin);
TextButton secondOptionButton = new TextButton(secondOption, skin);
TextButton thirdOptionButton = new TextButton(thirdOption, skin);

firstOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(0);
}
});

secondOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(1);
}
});

thirdOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(2);
}
});

window.defaults().spaceBottom(5);
window.row().fill().expandX();


window.row().padLeft(5);
Label label = new Label(texts, skin);
label.setWrap(true);
window.add(label).align(Align.LEFT).colspan(3).fillX();


window.row().fill().expandX().padTop(5);
window.add(firstOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.add(secondOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.add(thirdOptionButton).align(Align.CENTER).padLeft(5).padRight(5);

FlickScrollPane scrollPane = new FlickScrollPane(window);

scrollPane.width = Gdx.graphics.getWidth() * 0.5f;
scrollPane.height = Gdx.graphics.getHeight() * 0.6f;
scrollPane.x = Gdx.graphics.getWidth() * 0.5f - scrollPane.width * 0.5f;
scrollPane.y = Gdx.graphics.getHeight() * 0.6f - scrollPane.height * 0.6f;

return scrollPane;
}

public static Actor MissionDialog(String texts, final DialogListener dialogListener, String titleText, String firstOption, String secondOption, String thirdOption, String forthOption, Skin skin) {
Window window = new Window(titleText, skin);
window.setFillParent(false);
window.setMovable(true);

TextButton firstOptionButton = new TextButton(firstOption, skin);
TextButton secondOptionButton = new TextButton(secondOption, skin);
TextButton thirdOptionButton = new TextButton(thirdOption, skin);
TextButton forthOptionButton = new TextButton(forthOption, skin);
firstOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(0);
}
});

secondOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(1);
}
});

thirdOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(2);
}
});
forthOptionButton.setClickListener(new ClickListener() {
@Override
public void click(Actor actor, float x, float y) {
dialogListener.optionSelected(3);

}
});
if(Stardust3d.DEBUG) window.debug();
window.defaults().spaceBottom(5);
window.row();


window.row().padLeft(5);
Label label = new Label(texts, skin);
label.setWrap(true);
window.add(label).align(Align.LEFT).colspan(4).fillX();


window.row().padTop(5);
window.add(firstOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.add(secondOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.add(thirdOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.add(forthOptionButton).align(Align.CENTER).padLeft(5).padRight(5);
window.pack();
FlickScrollPane scrollPane = new FlickScrollPane(window);

scrollPane.width = Gdx.app.getGraphics().getWidth() * 0.75f;
scrollPane.height = Gdx.app.getGraphics().getHeight() * 0.75f;
scrollPane.x = Gdx.app.getGraphics().getWidth() * 0.5f - scrollPane.width * 0.5f;
scrollPane.y = Gdx.app.getGraphics().getHeight() * 0.5f - scrollPane.height * 0.5f;

return scrollPane;
}
}