package vn.enclave.peyton.fusion.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;

public class PlanTreeLableProvider extends LabelProvider {

    private static final long serialVersionUID = 5666977373586785596L;

    @Override
    public Image getImage(Object element) {
        if (element instanceof Plan) {
            return Constant.PLAN_IMAGE;
        } else if (element instanceof Project) {
            return Constant.PROJECT_IMAGE;
        } else if (element instanceof Version) {
            return Constant.VERSION_IMAGE;
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof Plan) {
            return ((Plan) element).getName();
        } else if (element instanceof Project) {
            return ((Project) element).getName();
        } else if (element instanceof Version) {
            return ((Version) element).getName();
        }
        return null;
    }
}