import {useEffect, useState} from "react";
import {Divider, notification} from "antd";
import ServerUrlTable from "./server-url-table";
import ServerUrlStatus from "./server-url-status";

export default function ServerUrl() {

    const [serverUrls, setServerUrls] = useState(null)
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setTimeout(fetchServerUrls, 1000);
        let interval = setInterval(fetchServerUrls, 5000);
        return () => clearInterval(interval);
    }, [])


    function fetchServerUrls() {
        setLoading(true)
        fetch(window.location.href + 'monitoring/all')
            .then(r => {
                if (r.ok) {
                    return r.json()
                }
                throw r
            })
            .then(r => {
                    setLoading(false)
                    setServerUrls(r.serverUrlDtoList)
                }
            )
            .catch(e => {
                setLoading(false)
                console.log(e)
                notification.error({
                    message: 'Error while reading data',
                    description: 'Error while reading data',
                    duration: 0,
                    placement: 'top',
                });
            })
    }

    return (
        <>
            <ServerUrlStatus loading={loading}/>
            <Divider/>
            <ServerUrlTable data={serverUrls}/>
        </>
    )
}