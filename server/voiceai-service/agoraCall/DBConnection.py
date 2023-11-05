from pymongo import MongoClient


connection_string = "mongodb+srv://S09P31C108:ckH4L78AnN@ssafy.ngivl.mongodb.net/S09P31C108?authSource=admin"

client = MongoClient(connection_string)
db = client['S09P31C108']
# pykrx_collection = db['pykrx']
realtime_collection = db['realtime']
test_collection = db['test']

@router.get("/dbtest")
def dbtest():
    return test_collection.find()