# import os

# import boto3

# def s3_connection():
#     try:
#         # s3 클라이언트 생성
#         s3 = boto3.client(
#           service_name="s3",
#           region_name="ap-northeast-2",
#           aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
#           aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
#         )
#     except Exception as e:
#         print(e)
#     else:
#         print("s3 bucket connected!") 
#         return s3

# # S3 연결 준비
# s3 = s3_connection()

