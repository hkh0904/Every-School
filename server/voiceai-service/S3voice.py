import os
from fastapi import APIRouter
import boto3

router = APIRouter()

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

@router.get("/v1/audiofiles")
def list_s3_files(userKey: str, otherUserKey: str):
    try:
        # S3 버킷에서 객체 목록 가져오기
        response = s3.list_objects(Bucket=os.getenv("S3_BUCKET"))

        # 가져온 객체 목록을 출력
        if 'Contents' in response:
            findStr = userKey.replace("-","") + "/" + otherUserKey.replace("-","")
            file_list = [obj['Key'] for obj in response['Contents'] if obj['Key'].startswith(findStr)]
            return {"files": file_list}
        else:
            return {"message": "No files in the S3 bucket"}

    except Exception as e:
        return {"error": str(e)}

