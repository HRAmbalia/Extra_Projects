import tkinter as tk
from YoutubeToMP3 import download_youtube_audio
from urllib.parse import urlparse
import re

def close_app():
    app.destroy()

def process_input():
    video_url = input_entry.get().strip()
    
    youtube_url_pattern = r"^(https?\:\/\/)?(www\.youtube\.com|youtu\.?be)\/.+$"
    if not re.match(youtube_url_pattern, video_url):
        feedback_label.config(text="Please enter a valid YouTube video URL.", fg="red")
        return

    # Disable the submit button during processing
    submit_button.config(state=tk.DISABLED)
    
    try:
        # Calling function with URL as parameter
        feedback_label.config(text="Downloading audio, please wait...", fg="blue")
        download_youtube_audio(video_url, '')
        feedback_label.config(text="Conversion completed.", fg="green")

        # Close the application window after a short delay (1000 milliseconds, i.e., 1 seconds)
        app.after(1500, close_app)
    except Exception as e:
        feedback_label.config(text=f"Error: {e}", fg="red")

    # Re-enable the submit button after processing
    submit_button.config(state=tk.NORMAL)

# Create the main application window
app = tk.Tk()
app.title("YouTube to MP3 Converter")

# Create a label for instructions
label = tk.Label(app, text="Enter the URL of the YouTube video you want to convert to MP3:")
label.pack(pady=10)

# Create the textbox for input
input_entry = tk.Entry(app, width=50)
input_entry.pack(pady=5)

# Create the submit button
submit_button = tk.Button(app, text="Convert to MP3", command=process_input)
submit_button.pack(pady=10)

# Create a label for feedback/status messages
feedback_label = tk.Label(app, text="", fg="black")
feedback_label.pack(pady=5)

# Start the main event loop
app.mainloop()
