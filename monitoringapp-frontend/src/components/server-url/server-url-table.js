import {Table} from "antd";

export default function ServerUrlTable(props) {

    const columns = [
        {
            title: 'Url',
            dataIndex: 'url',
            key: 'url',
            render(text, record) {
                return {
                    props: {
                        style: { color: record.result === 'OK' ? 'green' : 'red', fontWeight: 'bold'}
                    },
                    children: <div>{text}</div>
                };
            }
        },
        {
            title: 'Result',
            dataIndex: 'result',
            key: 'result',
            render(text, record) {
                return {
                    props: {
                        style: { color: record.result === 'OK' ? 'green' : 'red', fontWeight: 'bold'}
                    },
                    children: <div>{text}</div>
                };
            }
        }
    ]

    return (
        <Table dataSource={props.data} columns={columns}/>
    );

}