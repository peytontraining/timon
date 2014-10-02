package vn.enclave.peyton.fusion.common;

import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;

public abstract class AbstractTableViewPart extends ViewPart {

	protected TableViewer tableViewer;

	protected class ViewContentProvider implements IStructuredContentProvider {

		private static final long serialVersionUID = 2952528783056015184L;

		public ViewContentProvider() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			Object[] result = null;
			if (inputElement instanceof List<?>) {
				result = new Object[((List<Object[]>) inputElement).size()];
				((List<Object[]>) inputElement).toArray(result);
			}

			return result;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	protected class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private static final long serialVersionUID = 3997124367771097992L;

		public ViewLabelProvider() {
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			Object[] row = (Object[]) element;
			String result = row[columnIndex] == null ? "N/A" : row[columnIndex]
					.toString();
			return result;
		}
	}

	/**
	 * Sample code:<br>
	 * tableViewer = new TableViewer(parent, SWT.NONE);<br>
	 * tableViewer.setContentProvider(new ViewContentProvider());<br>
	 * tableViewer.setLabelProvider(new ViewLabelProvider());<br>
	 * final Table table = tableViewer.getTable();<br>
	 * tableViewer.setColumnProperties(initColumnProperties(table));<br>
	 */
	public abstract void createPartControl(Composite parent);

	@Override
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	protected abstract String[] initColumnProperties(Table table);
}
