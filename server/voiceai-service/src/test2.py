# import librosa
# import numpy as np
# from keras.models import Sequential
# from keras.layers import Dense, Dropout, Activation

# audio_file = "speech.wav"
# y, sr = librosa.load(audio_file)

# mfcc = librosa.feature.mfcc(y=y, sr=sr)  # MFCC 특징

# print(mfcc)
# # 감정 인식 모델에 입력하기 위해 데이터 전처리
# mfcc = np.expand_dims(mfcc, axis=0)  # 차원 확장
# print(mfcc)

from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
# import tarfile

# with tarfile.open('download.tar', 'r') as tar:
#     tar.extractall()
    
# 음성 데이터와 레이블을 로드합니다.
X, y = load_voice_data()

# 데이터를 학습용과 테스트용으로 분할합니다.
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# SVM 모델을 초기화하고 학습합니다.
svm_model = SVC(kernel='linear')
svm_model.fit(X_train, y_train)

# 테스트 데이터를 사용하여 모델을 평가합니다.
y_pred = svm_model.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)

print("Accuracy:", accuracy)