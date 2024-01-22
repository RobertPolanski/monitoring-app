import {Alert, Spin} from "antd";

export default function ServerUrlStatus(props) {
    return (
        <>
            <Spin spinning={props.loading}>
                <Alert type="info" message="List of servers urls" />
            </Spin>
            <div style={{marginTop: 16}}></div>
        </>
    );
}