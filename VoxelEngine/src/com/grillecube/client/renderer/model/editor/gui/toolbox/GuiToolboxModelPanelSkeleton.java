package com.grillecube.client.renderer.model.editor.gui.toolbox;

import com.grillecube.client.renderer.gui.GuiRenderer;
import com.grillecube.client.renderer.gui.components.GuiButton;
import com.grillecube.client.renderer.gui.components.GuiLabel;
import com.grillecube.client.renderer.gui.components.GuiSliderBar;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import com.grillecube.client.renderer.model.animation.Bone;
import com.grillecube.client.renderer.model.editor.gui.GuiSliderBarEditor;
import com.grillecube.client.renderer.model.editor.gui.GuiSpinnerEditor;

public class GuiToolboxModelPanelSkeleton extends GuiToolboxModelPanel {

	private final GuiButton addBone;
	private final GuiSpinnerEditor bones;
	private final GuiButton removeBone;

	private final GuiLabel boneTransformLabel;

	private final GuiSliderBarEditor posX;
	private final GuiSliderBarEditor posY;
	private final GuiSliderBarEditor posZ;

	private final GuiSliderBarEditor rotX;
	private final GuiSliderBarEditor rotY;
	private final GuiSliderBarEditor rotZ;

	public GuiToolboxModelPanelSkeleton() {
		super();
		this.addBone = new GuiButton();
		this.bones = new GuiSpinnerEditor();
		this.removeBone = new GuiButton();

		this.boneTransformLabel = new GuiLabel();

		this.posX = new GuiSliderBarEditor();
		this.posY = new GuiSliderBarEditor();
		this.posZ = new GuiSliderBarEditor();

		this.rotX = new GuiSliderBarEditor();
		this.rotY = new GuiSliderBarEditor();
		this.rotZ = new GuiSliderBarEditor();
	}

	public final void onInitialized(GuiRenderer guiRenderer) {
		{
			this.addChild(this.addBone);
			this.addChild(this.bones);
			this.addChild(this.removeBone);

			this.addBone.setText("Add");
			this.addBone.setBox(0.0f, 0.70f, 1 / 3.0f, 0.05f, 0.0f);
			this.addBone.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
			this.addBone.addTextParameter(new GuiTextParameterTextCenterBox());

			this.bones.setHint("Bones...");
			this.bones.setBox(1 / 3.0f, 0.70f, 1 / 3.0f, 0.05f, 0);

			this.removeBone.setText("Remove");
			this.removeBone.setBox(2 * 1 / 3.0f, 0.70f, 1 / 3.0f, 0.05f, 0.0f);
			this.removeBone.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
			this.removeBone.addTextParameter(new GuiTextParameterTextCenterBox());
		}

		{
			this.boneTransformLabel.setBox(0, 0.65f, 1, 0.05f, 0);
			this.boneTransformLabel.setText("Local Bone Transform");
			this.boneTransformLabel.setFontColor(0, 0, 0, 1.0f);
			this.boneTransformLabel.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
			this.boneTransformLabel.addTextParameter(new GuiTextParameterTextCenterBox());
			this.addChild(this.boneTransformLabel);
		}

		{
			this.addChild(this.posX);
			this.addChild(this.posY);
			this.addChild(this.posZ);
			this.posX.setBox(0, 0.60f, 1.0f, 0.05f, 0);
			this.posY.setBox(0, 0.55f, 1.0f, 0.05f, 0);
			this.posZ.setBox(0, 0.50f, 1.0f, 0.05f, 0);

			Object[] pos = (Object[]) GuiSliderBar.intRange(-16, 16);
			this.posX.addValuesArray(pos);
			this.posY.addValuesArray(pos);
			this.posZ.addValuesArray(pos);
			this.posX.setPrefix("Pos. X: ");
			this.posY.setPrefix("Pos. Y: ");
			this.posZ.setPrefix("Pos. Z: ");

			this.posX.select((Object) 0);
			this.posY.select((Object) 0);
			this.posZ.select((Object) 0);
		}

		{
			this.addChild(this.rotX);
			this.addChild(this.rotY);
			this.addChild(this.rotZ);

			this.rotX.setBox(0, 0.45f, 1.0f, 0.05f, 0);
			this.rotY.setBox(0, 0.40f, 1.0f, 0.05f, 0);
			this.rotZ.setBox(0, 0.35f, 1.0f, 0.05f, 0);

			Object[] rot = (Object[]) GuiSliderBar.intRange(-180, 180);
			this.rotX.addValuesArray(rot);
			this.rotY.addValuesArray(rot);
			this.rotZ.addValuesArray(rot);
			this.rotX.setPrefix("Rot. X: ");
			this.rotY.setPrefix("Rot. Y: ");
			this.rotZ.setPrefix("Rot. Z: ");

			this.rotX.select((Object) 0);
			this.rotY.select((Object) 0);
			this.rotZ.select((Object) 0);
		}

		this.refresh();
	}

	@Override
	public void refresh() {
		if (this.getBone() == null) {
			this.boneTransformLabel.setVisible(false);
			this.posX.setVisible(false);
			this.posY.setVisible(false);
			this.posZ.setVisible(false);
			this.rotX.setVisible(false);
			this.rotY.setVisible(false);
			this.rotZ.setVisible(false);
		}
	}

	private final Bone getBone() {
		return ((Bone) this.bones.getPickedObject());
	}

	@Override
	public String getTitle() {
		return ("Skeleton");
	}

}