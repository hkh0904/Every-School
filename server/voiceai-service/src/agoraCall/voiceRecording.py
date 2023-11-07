from fastapi import HTTPException, APIRouter
from fastapi.security import HTTPBasic, HTTPBasicCredentials
from fastapi.responses import JSONResponse
from datetime import datetime
from pydantic import BaseModel
from dotenv import load_dotenv
import os 
import httpx
import base64
import http.client

# load .env
load_dotenv()

security = HTTPBasic()
router = APIRouter()

def get_auth():
  # Customer ID
  customer_key = os.environ.get('AGORA_KEY')
  # Customer secret
  customer_secret = os.environ.get('AGORA_SECRET')

  # Concatenate customer key and customer secret and use base64 to encode the concatenated string
  credentials = customer_key + ":" + customer_secret
  # Encode with base64
  base64_credentials = base64.b64encode(credentials.encode("utf8"))
  credential = base64_credentials.decode("utf8")
  
  return credential

class RecordingItem(BaseModel):
    cname: str
    uid: str
    token: str
    userKey: str
    otherUserKey: str

@router.post("/v1/record/start")
async def record_start(item: RecordingItem):
    APP_ID = os.environ.get('APP_ID')

    API_URL = f"https://api.agora.io/v1/apps/{APP_ID}/cloud_recording/acquire"

    authorization = get_auth()

    # 요청 헤더 설정
    headers = {
        "Authorization": f"Basic {authorization}",
        "Content-Type": "application/json",
    }

    # 요청 본문 데이터
    acquire_request = {
        "cname": item.cname,
        "uid": item.uid,
        "clientRequest": {
            "resourceExpiredHour": 24,
            "scene": 0
        }
    }

    response_acquire = {}
    # acquire 요청
    async with httpx.AsyncClient() as client:
        response = await client.post(API_URL, headers=headers, json=acquire_request)
        
        # 응답 처리
        if response.status_code == 200:
            # 응답이 200 OK인 경우, 응답 내용을 JSON으로 파싱하여 반환
            response_acquire = response.json()
        else:
            # 응답이 200 OK가 아닌 경우, 오류 응답 반환
            raise HTTPException(status_code=response.status_code, detail="Failed to request the external API")
    
    print(response_acquire)

    # start 요청
    resourceId = response_acquire.get("resourceId")
    API_URL = f"https://api.agora.io/v1/apps/{APP_ID}/cloud_recording/resourceid/{resourceId}/mode/mix/start"

    start_request = {
        "uid": item.uid,
        "cname": item.cname,
        "clientRequest": {
            "token": item.token,
            "recordingConfig": {
                # 30초 이후 녹화 채널에 사용자가 없으면 자동으로 녹화를 중지
                "maxIdleTime": 30,
                # 구독할 미디어 스트림 유형 (0: 오디오만)
                "streamTypes": 0,
                "audioProfile": 1,
                # (기본값: 0) 통신 프로필입니다.
                "channelType": 0,
                "subscribeAudioUids": []
            },
            # "recordingFileConfig": {
            #     "avFileType": [
            #         "m4a"  # MP3 형식만 활성화
            #     ]
            # },
            "storageConfig": {
                "accessKey": os.environ.get('CREDENTIALS_ACCESS_KEY'),
                "region": 11,
                "bucket": os.environ.get('S3_BUCKET'),
                "secretKey": os.environ.get('CREDENTIALS_SECRET_KEY'),
                "vendor": 1,
                "fileNamePrefix": [
                    item.userKey.replace("-",""),
                    item.otherUserKey.replace("-",""),
                    datetime.today().strftime("%Y%m%d%H%M") 
                ]
            }
        }
    }

    async with httpx.AsyncClient() as client:
        response = await client.post(API_URL, headers=headers, json=start_request)
        
        # 응답 처리
        if response.status_code == 200:
            # 응답이 200 OK인 경우, 응답 내용을 JSON으로 파싱하여 반환
            response_start = response.json()
            return JSONResponse(content=response_start)
        else:
            # 응답이 200 OK가 아닌 경우, 오류 응답 반환
            raise HTTPException(status_code=response.status_code, detail="Failed to request the external API")

   
class StopItem(BaseModel):
    cname: str
    uid: str
    resourceId: str
    sid: str

@router.post("/v1/record/stop")
async def record_stop(item: StopItem):
    print("asfd")
    APP_ID = os.environ.get('APP_ID')

    API_URL = f'http://api.agora.io/v1/apps/{APP_ID}/cloud_recording/resourceid/{item.resourceId}/sid/{item.sid}/mode/mix/stop'
    print(API_URL)

    authorization = get_auth()

    # 요청 헤더 설정
    headers = {
        "Authorization": f"Basic {authorization}",
        "Content-Type": "application/json",
    }

    # 요청 본문 데이터
    stop_request = {
        "cname": item.cname,
        "uid": item.uid,
        "clientRequest": {
            "async_stop": False 
        }
    }

    async with httpx.AsyncClient() as client:
        response = await client.post(API_URL, headers=headers, json=stop_request)
        
        # 응답 처리
        if response.status_code == 200:
            # 응답이 200 OK인 경우, 응답 내용을 JSON으로 파싱하여 반환
            response_stop = response.json()

            response_data = {
                'cname': response_stop.get("cname"),
                'uid': response_stop.get("uid"),
                'resourceId': response_stop.get("resourceId"),
                'sid': response_stop.get("sid"),
                'fileDir': response_stop.get("serverResponse").get("fileList"),
                'uploadStatus': response_stop.get("serverResponse").get("uploadingStatus"),
            }

            return JSONResponse(content=response_data)
        else:
            # 응답이 200 OK가 아닌 경우, 오류 응답 반환
            raise HTTPException(status_code=response.status_code, detail="Failed to request the external API")

