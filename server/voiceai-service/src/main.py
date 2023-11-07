from fastapi import FastAPI, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from typing import Union
from py_eureka_client.eureka_client import EurekaClient
from pydantic import BaseModel
# from S3voice import s3_connection
from pydantic import BaseModel
from agoraCall import voiceRecording as vr_router
from S3voice import router as s3_router
import os
import requests
import httpx

from dotenv import load_dotenv
load_dotenv()


INSTANCE_PORT = 9002
# 유레카 관련 설정
INSTANCE_HOST = "every-school.com"
# INSTANCE_HOST = "127.0.0.1"

app = FastAPI()

app.include_router(vr_router.router)
app.include_router(s3_router)

@app.on_event("startup")
async def eureka_init():
    global client
    client = EurekaClient(
        eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        app_name="voiceai-service",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
@app.on_event("shutdown")
async def destroy():
    await client.stop()

@app.get("/v1/index")
def index():
    return {"message": "Welcome to voice ai server"}

async def stt(audio_data):

    API_URL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=Kor"

    # 요청 헤더 설정
    headers = {
        "X-NCP-APIGW-API-KEY-ID": os.getenv("X-NCP-APIGW-API-KEY-ID"),
        "X-NCP-APIGW-API-KEY": os.getenv("X-NCP-APIGW-API-KEY"),
        "Content-Type": "application/octet-stream",
    }

    async with httpx.AsyncClient(timeout=60) as client:
        response = await client.post(API_URL, headers=headers, data=audio_data)
        
        # 응답 처리
        if response.status_code == 200:
            # 응답이 200 OK인 경우, 응답 내용을 JSON으로 파싱하여 반환
            response = response.json()

            return response
        else:
            # 응답이 200 OK가 아닌 경우, 오류 응답 반환
            raise HTTPException(status_code=response.status_code, detail="Failed to request the external API")

async def sentiment(msg):
    API_URL = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze"

    # 요청 헤더 설정
    headers = {
        "X-NCP-APIGW-API-KEY-ID": os.getenv("X-NCP-APIGW-API-KEY-ID"),
        "X-NCP-APIGW-API-KEY": os.getenv("X-NCP-APIGW-API-KEY"),
        "Content-Type": "application/json",
    }

    request_data = {
        "content": msg,
        "config.negativeClassification": False
    }

    async with httpx.AsyncClient(timeout=60) as client:
        response = await client.post(API_URL, headers=headers, json=request_data)
        
        # 응답 처리
        if response.status_code == 200:
            # 응답이 200 OK인 경우, 응답 내용을 JSON으로 파싱하여 반환
            response = response.json()
            
            return response
        else:
            # 응답이 200 OK가 아닌 경우, 오류 응답 반환
            raise HTTPException(status_code=response.status_code, detail="Failed to request the external API")




@app.post("/v1/record/analysis")
async def record_analysis(file: UploadFile):

    # 업로드된 파일의 내용을 읽어서 바이너리로 저장
    audio_data = file.file.read()

    # # POST 요청 보내기
    # result = await stt(audio_data=audio_data)

    # sentiment_result = await sentiment(msg=result["text"])

    # 병렬로 요청 보내기
    stt_task = stt(audio_data)
    stt_result = await stt_task

    # 요청 결과 기다리기
    sentiment_task = sentiment(stt_result["text"])
    sentiment_result = await sentiment_task

    detailResult = []
    for info in sentiment_result["sentences"]:
        temp = {}
        temp["content"] = info["content"]
        temp["offset"] = info["offset"]
        temp["length"] = info["length"]
        temp["sentiment"] = info["sentiment"]
        temp["confidence"] = [round(info["confidence"]["neutral"] * 100, 3), round(info["confidence"]["positive"] * 100, 3), round(info["confidence"]["negative"] * 100, 3)]
        detailResult.append(temp)

    response_data = {
        "overallResult": sentiment_result["document"]["sentiment"],
        "overallPercent": [round(sentiment_result["document"]["confidence"]["neutral"], 3), round(sentiment_result["document"]["confidence"]["positive"], 3), round(sentiment_result["document"]["confidence"]["negative"],3 )],
        "detailsResult": detailResult
    }

    return JSONResponse(content=response_data)

