package databaseProject;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class FishTable extends AbstractTableModel {
	Vector row = new Vector();
	Vector col = new Vector();
	
	FishTable() {
		col.add("분류");
		col.add("이름");
		col.add("학명");
		col.add("크기");
		col.add("색상");
		col.add("성격");
		col.add("최근 수정일");
	}
	
	public String getColumnName(int idx) {
		return String.valueOf(col.get(idx));
	}
	
	public void setTable(Vector r) {
		this.row = r;
	}
	
	@Override
	public int getColumnCount() {
		return col.size();
	}
	
	@Override
	public int getRowCount() {
		return row.size();
	}
	
	@Override
	public Object getValueAt(int r, int c) {
		Vector v = (Vector)row.get(r);
		return v.get(c);
	}
}
