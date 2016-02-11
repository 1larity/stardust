/*
 * Copyright 2012 Richard Beech rp.beech@gmail.com
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.digitale.mygdxgame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class DecalSprite {

        public Decal sprite;

        public DecalSprite() {
                // constructor
        }

        public DecalSprite build(String imgPath) {
                TextureWrap texWrap = Texture.TextureWrap.ClampToEdge; // default
                return build(imgPath, texWrap);
        }

        public DecalSprite build(String imgPath, TextureWrap texWrap) {
                Texture image = new Texture(Gdx.files.internal(imgPath));
                image.setFilter(Texture.TextureFilter.Linear,
                                Texture.TextureFilter.Linear);
                image.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

                float w = image.getWidth();
                float h = image.getHeight();
                sprite = Decal.newDecal(w, h, new TextureRegion(image), true);
                return this;
        }

        public void faceCamera(Camera oCam) {
                sprite.lookAt(oCam.position.cpy(), oCam.up.cpy().nor());
        }

        public void update(float delta) {
                // sprite.setRotation(dir, up)
        }
}
