from fastapi import FastAPI
from typing import Union
from py_eureka_client.eureka_client import EurekaClient
from pydantic import BaseModel

# 유레카 관련 설정
INSTANCE_PORT = 9002
INSTANCE_HOST = "k9c108.p.ssafy.io"

app = FastAPI()

@app.on_event("startup")
async def eureka_init():
    global client
    client = EurekaClient(
        # eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        eureka_server=f"http://localhost:8761/eureka",
        app_name="voiceai-service",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
@app.on_event("shutdown")
async def destroy():
    await client.stop()

@app.get("/index")
def index():
    return {"message": "Welcome to chat server"}


class Item(BaseModel):
    name: str
    price: float
    is_offer: Union[bool, None] = None


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}


@app.put("/items/{item_id}")
def update_item(item_id: int, item: Item):
    return {"item_name": item.name, "item_id": item_id}
