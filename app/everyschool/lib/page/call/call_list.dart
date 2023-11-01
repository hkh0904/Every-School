import 'package:flutter/material.dart';

class CallList extends StatefulWidget {
  const CallList({super.key, this.join, this.leave});
  final join;
  final leave;

  @override
  State<CallList> createState() => _CallListState();
}

class _CallListState extends State<CallList> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: SizedBox(
        height: MediaQuery.of(context).size.height,
        child: ListView(
          children: [
            Row(
              children: <Widget>[
                Expanded(
                  child: ElevatedButton(
                    child: const Text("Join"),
                    onPressed: () => {widget.join()},
                  ),
                ),
                const SizedBox(width: 10),
                Expanded(
                  child: ElevatedButton(
                    child: const Text("Leave"),
                    onPressed: () => {widget.leave()},
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    ));
  }
}
