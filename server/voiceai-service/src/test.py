import librosa
import numpy as np
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation
import boto3
import os
# audio_file = "speech.wav"
# y, sr = librosa.load(audio_file)

# print(y)
# print(len(y))
# print(sr)
# print(len(y) / sr)
# mfcc = librosa.feature.mfcc(y=y, sr=sr)
# print(mfcc)

# import IPython.display as ipd
# ipd.Audio(y, rate=sr)

# import matplotlib.pyplot as plt
# import librosa.display

# plt.figure(figsize =(16,6))
# librosa.display.waveshow(y,sr=sr)
# plt.show()

# D = np.abs(librosa.stft(y, n_fft=2048, hop_length=512)) #n_fft : window size / 이 때, 음성의 길이를 얼마만큼으로 자를 것인가? 를 window라고 부른다.

# print(D.shape)

# plt.figure(figsize=(16,6))
# plt.plot(D)
# plt.show()


# DB = librosa.amplitude_to_db(D, ref=np.max) #amplitude(진폭) -> DB(데시벨)로 바꿔라

# plt.figure(figsize=(16,6))
# librosa.display.specshow(DB,sr=sr, hop_length=512, x_axis='time', y_axis='log')
# plt.colorbar()
# plt.show()
from dotenv import load_dotenv
import m3u8
from pydub import AudioSegment

load_dotenv()

def s3_connection():
    try:
        # s3 클라이언트 생성
        s3 = boto3.client(
          service_name="s3",
          region_name="ap-northeast-2",
          aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
          aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
        )
    except Exception as e:
        print(e)
    else:
        print("s3 bucket connected!") 
        return s3

# S3 연결 준비
s3 = s3_connection()

# S3에서 m3u8 파일 다운로드
m3u8_file_key = "5b657354461e40e79da08ec7dfcd46da/5b657354123440e79da08ec7dfcd46da/20231103/531b3df29d494d5705da4f945e3444f2_test.m3u8"
response = s3.get_object(Bucket=os.getenv("S3_BUCKET"), Key=m3u8_file_key)
m3u8_content = response['Body'].read().decode('utf-8')

# M3U8 파싱
m3u8_obj = m3u8.loads(m3u8_content)
segments = m3u8_obj.segments

# TS 파일 
for segment in segments:
    ts_file_key = segment.uri
    ts_local_path = os.path.join("./", os.path.basename(ts_file_key))

    response = s3.get_object(Bucket=os.getenv("S3_BUCKET"), Key="5b657354461e40e79da08ec7dfcd46da/5b657354123440e79da08ec7dfcd46da/20231103/" + ts_file_key)
    ts_data = response['Body'].read()

    # TS 파일을 로컬 디렉토리에 저장
    with open(ts_local_path, 'wb') as ts_file:
        ts_file.write(ts_data)
        print(f"Downloaded {ts_file_key}")

print("Download completed.")

# print("Conversion completed.")

# mfcc = np.expand_dims(mfcc, axis=0)

# print(mfcc)
# model = Sequential()
# model.add(Dense(256, input_dim=mfcc.shape[1]))
# model.add(Activation('relu'))
# model.add(Dropout(0.5))
# model.add(Dense(128))
# model.add(Activation('relu'))
# model.add(Dropout(0.5))
# model.add(Dense(7))
# model.add(Activation('softmax'))

# model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
# model.fit(mfcc, labels, epochs=10, batch_size=32)

# print(model)