import pandas as pd
from bisect import bisect_left
import time

# Function to find the index of the closest weather timestamp
def find_closest_timestamp_index(timestamp, weather_timestamps):
    pos = bisect_left(weather_timestamps, timestamp)
    if pos == 0:
        return 0
    if pos == len(weather_timestamps):
        return len(weather_timestamps) - 1
    before = weather_timestamps[pos - 1]
    after = weather_timestamps[pos]
    if after - timestamp < timestamp - before:
        return pos
    else:
        return pos - 1

def process_data(crimes_file_path, accidents_file_path, output_file_path):
    # Read crimes data with specified quote character
    los_angeles_crimes_df = pd.read_csv(crimes_file_path)
    us_accidents_df = pd.read_csv(accidents_file_path, chunksize=100000)  # Load in chunks

    # Convert weather timestamp to datetime
    weather_chunks = []
    for chunk in us_accidents_df:
        chunk['Weather_Timestamp'] = pd.to_datetime(chunk['Weather_Timestamp'], format='%Y-%m-%d %H:%M:%S')
        weather_chunks.append(chunk[chunk['City'] == 'Los Angeles'])

    los_angeles_accidents_df = pd.concat(weather_chunks, ignore_index=True)
    los_angeles_accidents_df = los_angeles_accidents_df[los_angeles_accidents_df['Weather_Timestamp'].dt.year >= 2020]

    los_angeles_weather_df = los_angeles_accidents_df[
        ["Weather_Timestamp", "Temperature(F)", "Wind_Chill(F)", "Humidity(%)", "Pressure(in)", "Visibility(mi)",
         "Wind_Direction", "Wind_Speed(mph)", "Precipitation(in)", "Weather_Condition", "Sunrise_Sunset",
         "Civil_Twilight", "Nautical_Twilight", "Astronomical_Twilight"]]
    # Drop duplicate records based on 'Weather_Timestamp' column
    los_angeles_weather_df = los_angeles_weather_df.drop_duplicates(subset=['Weather_Timestamp'])

    # Convert DATE OCC to datetime, ignoring the time part
    los_angeles_crimes_df['DATE OCC'] = pd.to_datetime(los_angeles_crimes_df['DATE OCC'].str.split().str[0], format='%m/%d/%Y')

    # Combine DATE OCC and TIME OCC into a single datetime column
    los_angeles_crimes_df['TIME OCC'] = los_angeles_crimes_df['TIME OCC'].apply(lambda x: '{:04d}'.format(x))
    los_angeles_crimes_df['DATE OCC'] = pd.to_datetime(
        los_angeles_crimes_df['DATE OCC'].astype(str) + ' ' + los_angeles_crimes_df['TIME OCC'],
        format='%Y-%m-%d %H%M'
    )

    los_angeles_crimes_df = los_angeles_crimes_df[
        ["DATE OCC", 'AREA', 'AREA NAME', 'Crm Cd', 'Crm Cd Desc', 'Vict Age', 'Vict Sex', 'Vict Descent', 'Premis Cd', 'Premis Desc',
         'Weapon Used Cd', 'Weapon Desc', 'LOCATION', 'LAT', 'LON']]

    # Sort weather dataframe by Weather_Timestamp
    los_angeles_weather_df = los_angeles_weather_df.sort_values('Weather_Timestamp').reset_index(drop=True)
    weather_timestamps = los_angeles_weather_df['Weather_Timestamp'].values

    combined_records = []
    # Iterate over each record in the crime dataframe
    for idx, crime_record in los_angeles_crimes_df.iterrows():
        # Find the index of the closest weather data
        closest_weather_index = find_closest_timestamp_index(crime_record['DATE OCC'], weather_timestamps)
        # Extract the corresponding weather data
        closest_weather_data = los_angeles_weather_df.iloc[closest_weather_index]
        # Combine the crime record with the closest weather data
        combined_record = pd.concat([crime_record, closest_weather_data], axis=0)
        # Append the combined record to the list
        combined_records.append(combined_record)

    # Convert the list of combined records to a DataFrame
    combined_df = pd.DataFrame(combined_records)

    # Reset index to maintain a clean DataFrame structure
    combined_df.reset_index(drop=True, inplace=True)

    # Write the combined DataFrame to CSV with specified quote character
    combined_df.to_csv(output_file_path, index=False, date_format='%Y-%m-%d %H:%M:%S')

    print("Successfully finished execution")

def main():
    start_time = time.time()

    crimes_file_path = "./data/los_angeles_crimes.csv"
    accidents_file_path = "./data/us_accidents.csv"
    output_file_path = './data/crimes_weather.csv'

    process_data(crimes_file_path, accidents_file_path, output_file_path)

    end_time = time.time()
    execution_time = end_time - start_time
    print(f"Execution time: {execution_time:.2f} seconds")

if __name__ == '__main__':
    main()
