FROM python:3.11

COPY src/ ./

COPY requirements.txt ./requirements.txt

COPY .env ./.env

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 9002

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "9002", "--reload"]
