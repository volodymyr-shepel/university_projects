from tkinter import * 
from tkinter import ttk
from tkinter import messagebox
import os
import sqlite3
from database import databaseCreation
from datetime import datetime
from hltv_parser import hltv_parser
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
# this is the class to display the table with top 30 teams in ranking

class Table:
     
    def __init__(self,frame,data,header_row):
    
        for j in range(len(header_row)):
            e = Entry(frame, width=26,font=('Arial', 11, 'bold'),fg = "#158aff")
            e.grid(row=0, column=j)  # Row 0 for the header row
            e.insert(END, header_row[j])
        # code for creating table
        for i in range(len(data)):
            for j in range(len(data[0])):
                 
                self.e = Entry(frame, width=26,
                               font=('Arial',11,'bold'))
                 
                self.e.grid(row=i + 1, column=j)
                self.e.insert(END, data[i][j])

# used to read the data from the table
def read_data():    
    conn = sqlite3.connect('hltv.db')
    cursor = conn.cursor()

    # Execute a SELECT query to fetch the data from the database
    cursor.execute('SELECT * FROM teams')

    # Fetch all rows from the result set
    rows = cursor.fetchall()

    # Close the database connection
    conn.close()   
    
    data = [list(row) for row in rows]
    return data

# this is home_page(when user clicks home it will display this)
def home_page():
    for widget in main_frame.winfo_children():
            widget.destroy()
    indicate(home_indicate)
    if not os.path.isfile("hltv.db"):
        # used to disable the menu buttons if the database is not loaded
        stat_btn.config(state=DISABLED)
        graph_btn.config(state=DISABLED)
        settings_btn.config(state = DISABLED)
        # -----------------------------------
        
        start_frame = Frame(main_frame)
        start_frame.place(anchor="c", relx=.5, rely=.5)
        label = Label(start_frame,text = "There is no database. Press the button to load the database",font = ('Arial',13,'bold'))
        load_button = Button(start_frame,text = "Load database",command = load_data)
        label.grid(row=0, column=0, padx=10, pady=10)
        load_button.grid(row=1, column=0, padx=10, pady=10)
    else:
        # used to enable the menu buttons if the database is loaded
        stat_btn.config(state=NORMAL)
        graph_btn.config(state=NORMAL)
        settings_btn.config(state = NORMAL)
        # -----------------------
        data = read_data()
        header_row = ["Team id", "Team name", "Ranking points"]
        t = Table(main_frame,data,header_row)

# this the stat_page
def stat_page():
    
    for widget in main_frame.winfo_children():
            widget.destroy()
    indicate(stat_indicate)
    
    
    stat_operations_frame = Frame(main_frame)
    stat_operations_frame.pack(side = TOP)
    
    
    feature_label = ttk.Label(stat_operations_frame,text="Please select a feature:",foreground="#158aff")
    feature_label.grid(row=0,column=0,padx=5,pady=5)
    
    # combobox used to select feature
    global selected_feature
    selected_feature = StringVar()
    feature_cb = ttk.Combobox(stat_operations_frame, textvariable=selected_feature)
    
    feature_cb['values'] = ["Rating","kills_per_round","headshots","maps_played","deaths_per_round","rounds_contributed"]
    
    # prevent typing a value
    feature_cb['state'] = 'readonly'
    
    feature_cb.grid(row = 0,column=1,padx = 5,pady = 5)
    
    
    
    operation_label = ttk.Label(stat_operations_frame,text="Please select an operation:",foreground="#158aff")
    operation_label.grid(row = 1,column = 0,padx = 5,pady = 5)
    
    # combobox used to select feature
    
    global selected_operation
    selected_operation= StringVar()
    operation_cb = ttk.Combobox(stat_operations_frame, textvariable=selected_operation)
    
    operation_cb['values'] = ["Avg","Max","Min"]
    
    # prevent typing a value
    operation_cb['state'] = 'readonly'
    
    operation_cb.grid(row = 1,column=1,padx=5, pady = 5)
    
    button = ttk.Button(stat_operations_frame, text="Submit",command = calculate_statistics_stats)
    button.grid(row=2, columnspan=2, padx=5, pady=5)

# it is used to calculate statistics on stats_page
def calculate_statistics_stats():
    get_feature = selected_feature.get()
    get_operation = selected_operation.get()
    try:
        # Connect to the database
        conn = sqlite3.connect('hltv.db')
        cursor = conn.cursor()

        # Execute the SQL statement
        sorting_order = "DESC" if (get_operation == 'Max' or get_operation == 'Avg') else "ASC"
        
        cursor.execute(f'''
            SELECT t.team_id, t.team_name, ROUND({get_operation.upper()}(ps.{get_feature}),2) AS {get_operation.lower()}_{get_feature.lower()}
            FROM teams t
            JOIN players p ON t.team_id = p.team_id
            JOIN players_stats ps ON p.player_id = ps.player_id
            GROUP BY t.team_id, t.team_name
            ORDER BY {get_operation.lower()}_{get_feature.lower()} {sorting_order};
        ''')
        
        

        # Fetch all the rows returned by the query
        rows = cursor.fetchall()
        data = [list(row) for row in rows]
        header_row = ["team_id","team_name",f"{get_operation}_{get_feature}"]
        t = Table(main_frame,data,header_row)

        display_result(f"Calculating statistics {get_operation} for feature {get_feature}","Success")
    except Exception as e:
        
        display_result(f"Calculating statistics {get_operation} for feature {get_feature}","Failure",str(e) + "\n")

    finally:
        conn.close()
    
    
def calculate_statistics_graph():
    for widget in main_frame.winfo_children():
            widget.destroy()
            
    get_feature_graph = selected_feature_graph.get()
    get_operation_graph = selected_operation_graph.get()
    get_number_of_players = number_of_players_graph.get()   
    
    try:
        # Connect to the database
        conn = sqlite3.connect('hltv.db')
        cursor = conn.cursor()
        sorting_order = "DESC" if get_operation_graph == "Top" else "ASC"

        cursor.execute(f'''
            SELECT  players_stats.{get_feature_graph}, players.player_nickname
            FROM players
            JOIN players_stats ON players.player_id = players_stats.player_id
            ORDER BY players_stats.{get_feature_graph} {sorting_order}
            LIMIT {get_number_of_players};

        ''')
        
        # Fetch all the rows returned by the query
        rows = cursor.fetchall()
        data = [list(row) for row in rows]
        
        players = [entry[1] for entry in data]
        ratings = [entry[0] for entry in data]

        # Create a bar plot
        fig, ax = plt.subplots(figsize=(8, 7))  # Adjust the figure size as needed
        if graph_type_var.get() == 1:
            ax.bar(players, ratings)
        else:
            ax.plot(players,ratings)
        ax.set_xlabel('Player Name')
        ax.set_ylabel(get_feature_graph)
        ax.set_title(f'Player {get_feature_graph}')

        plt.xticks(rotation=90)
        
        # Display the plot within the Tkinter frame
        fig = plt.gcf()
        canvas = FigureCanvasTkAgg(fig, master=main_frame)
        canvas.draw()
        canvas.get_tk_widget().pack(anchor="center")
        
        display_result(f"Displaying graph: {get_operation_graph} players for feature {get_feature_graph} with number of players equal to {get_number_of_players}","Success")
        
        
        
    except Exception as e:
        display_result(f"Displaying graph: {get_operation_graph} players for feature {get_feature_graph} with number of players equal to {get_number_of_players}","Failure",str(e) + "\n")

    finally:
        conn.close()
def delete_data():
    operation = "Delete data"
    if messagebox.askokcancel(title = "Watch out",message = "Are you sure you want to delete the database?"):
        try:
            if os.path.isfile("hltv.db"):
                os.remove("hltv.db")
                display_result(operation,"Success")
                home_page()
        except Exception as e:
            
            display_result(operation,"Failure",str(e) + "\n")
     
            
        
# this the settings page
def settings_page():
    for widget in main_frame.winfo_children():
            widget.destroy()
    indicate(settings_indicate)
    
    settings_frame = Frame(main_frame)
    settings_frame.place(anchor="c", relx=.5, rely=.5)
    label = Label(settings_frame,text = "To delete the database press the button",font = ('Arial',13,'bold'))
    load_button = Button(settings_frame,text = "Delete database",command = delete_data)
    label.grid(row=0, column=0, padx=10, pady=10)
    load_button.grid(row=1, column=0, padx=10, pady=10)
    

def graph_page():
    for widget in main_frame.winfo_children():
            widget.destroy()
    indicate(graph_indicate)
    
    
    graph_operations_frame = Frame(main_frame)
    graph_operations_frame.pack(side = TOP)
    
    
    feature_label = ttk.Label(graph_operations_frame,text="Please select a feature:",foreground="#158aff")
    feature_label.grid(row=0,column=0,padx=5,pady=5)
    
    # combobox used to select feature
    global selected_feature_graph
    selected_feature_graph = StringVar()
    feature_cb = ttk.Combobox(graph_operations_frame, textvariable=selected_feature_graph)
    
    feature_cb['values'] = ["Rating","kills_per_round","headshots","maps_played","deaths_per_round","rounds_contributed"]
    
    # prevent typing a value
    feature_cb['state'] = 'readonly'
    
    feature_cb.grid(row = 0,column=1,padx = 5,pady = 5)
    
    
    
    operation_label = ttk.Label(graph_operations_frame,text="Please select an operation:",foreground="#158aff")
    operation_label.grid(row = 1,column = 0,padx = 5,pady = 5)
    
    # combobox used to select feature
    
    global selected_operation_graph
    selected_operation_graph = StringVar()
    operation_cb = ttk.Combobox(graph_operations_frame, textvariable=selected_operation_graph)
    
    operation_cb['values'] = ["Top","Worst"]
    
    # prevent typing a value
    operation_cb['state'] = 'readonly'
    
    operation_cb.grid(row = 1,column=1,padx=5, pady = 5)
    
    operation_label = ttk.Label(graph_operations_frame,text="Please select number of players to display:",foreground="#158aff")
    operation_label.grid(row = 2,column = 0,padx = 5,pady = 5)
    
    
    # combobox used to select number of players to display on the graph
    
    global number_of_players_graph
    number_of_players_graph = StringVar()
    operation_cb = ttk.Combobox(graph_operations_frame, textvariable=number_of_players_graph)
    
    operation_cb['values'] = [5,10,20]
    
    # prevent typing a value
    operation_cb['state'] = 'readonly'
    
    operation_cb.grid(row = 2,column=1,padx=5, pady = 5)
    
    graph_type_label = ttk.Label(graph_operations_frame,text="Please select type of the graph:",foreground="#158aff")
    graph_type_label.grid(row = 3,column = 0,padx = 5,pady = 5)
    
    global graph_type_var
    graph_type_var = IntVar()
    
    radio_button1 = Radiobutton(graph_operations_frame, text="BarPlot", variable=graph_type_var, value=1)
    radio_button1.grid(row = 3,column = 1,padx = 5,pady = 5)
    
    radio_button2 = Radiobutton(graph_operations_frame, text="Plot", variable=graph_type_var, value=2)
    radio_button2.grid(row = 3,column = 2,padx = 5,pady = 5)
    
    
    button = ttk.Button(graph_operations_frame, text="Submit",command = calculate_statistics_graph)
    button.grid(row=4, columnspan=3, padx=5, pady=5)

# used to write the result of last operation
def display_result(operation,result,error = ""):
    text_section.delete("1.0", END)
    # Get the current timestamp
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    text_section.insert(END, f"Operation {operation} finished with {result}.\n" + error)
    text_section.insert(END, "Performed at: {}\n".format(timestamp))

# used to load the data from the json files to the database
def load_data():
    operation = "Load data"
    try:
        databaseCreation.create_and_fill()
        display_result(operation,"Success")
        home_page()
    except Exception as e:
        display_result(operation,"Failure",str(e) + "\n")

# used to hide all other indicators(so it displays only indicator of the current page)
def hide_indicators():
    home_indicate.config(bg='#c3c3c3')
    settings_indicate.config(bg='#c3c3c3')
    graph_indicate.config(bg='#c3c3c3')
    stat_indicate.config(bg='#c3c3c3')

# used to indicate the current page(blue rectangle)   
def indicate(lb):
    hide_indicators()
    lb.config(bg = '#158aff')

# setup
root = Tk()
icon = PhotoImage(file = "hltv_image.png")

# add the root window
root.geometry("800x800")
root.title("hltv.org")
root.iconphoto(True,icon)
root.resizable(False,False)

# add the options frame which will be on the left side it will be as menu
options_frame = Frame(root,bg = '#c3c3c3')
options_frame.pack(side = LEFT)
options_frame.pack_propagate(False)
options_frame.configure(width =200,height = 800)

# add main frame, all the data will be displayed here
main_frame = Frame(root,highlightbackground='black',highlightthickness=2)
main_frame.pack(side= TOP,fill="both", expand=True)
main_frame.pack_propagate(False)
main_frame.configure(height=650,width = 600)

# add status_frame in the bottom it will display information about result of last operation
status_frame = Frame(root,highlightbackground='red',highlightthickness=2)
status_frame.pack(side= BOTTOM)
status_frame.pack_propagate(False)
status_frame.configure(height=150,width = 600)

# add text_section for status_frame(the actual test will be displayed here)
text_section = Text(status_frame, height=150, width=600)
text_section.pack()

# add home_btn to options_frame
home_btn = Button(options_frame,text = "Home",font = ('Bold',18),
                  fg = "#158aff",bd = 0,bg = "#c3c3c3",command=home_page)


home_btn.place(x = 50,y = 100)

home_indicate = Label(options_frame,text = "",bg = '#c3c3c3',height=2)
home_indicate.place(x = 43,y = 100)

# add stat_btn to options_frame
stat_btn = Button(options_frame,text = "Stats",font = ('Bold',18),
                  fg = "#158aff",bd = 0,bg = "#c3c3c3",command=stat_page)

stat_btn.place(x = 50,y = 250)
stat_indicate = Label(options_frame,text = "",bg = '#c3c3c3',height=2)
stat_indicate.place(x = 43,y = 250)

# add graph_btn to options_frame
graph_btn = Button(options_frame,text = "Graph",font = ('Bold',18),
                  fg = "#158aff",bd = 0,bg = "#c3c3c3",command = graph_page)

graph_btn.place(x = 50,y = 400)

graph_indicate = Label(options_frame,text = "",bg = '#c3c3c3',height=2)
graph_indicate.place(x = 43,y = 400)

# add settings_btn to options_frame
settings_btn = Button(options_frame,text = "Settings",font = ('Bold',18),
                  fg = "#158aff",bd = 0,bg = "#c3c3c3",command=settings_page)

settings_btn.place(x = 50,y = 550)

settings_indicate = Label(options_frame,text = "",bg = '#c3c3c3',height=2)
settings_indicate.place(x = 43,y = 550)

home_page()
    

    
    
    

    

root.mainloop()

