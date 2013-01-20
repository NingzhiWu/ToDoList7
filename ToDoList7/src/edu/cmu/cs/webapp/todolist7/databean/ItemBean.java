
package edu.cmu.cs.webapp.todolist7.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class ItemBean {
	private int    id;
	private String item;
	private int    position;
	
	public int    getId()            { return id;       }
    public String getItem()          { return item;     }
    public int    getPosition()      { return position; }

    public void   setId(int i)       { id = i;          }
	public void   setItem(String s)  { item = s;        }
	public void   setPosition(int i) { position = i;    }
}
