import './App.css';
import React from 'react';
import './index.css';
import { Layout, Flex } from 'antd';
import ServerUrl from "./components/server-url/server-url";
const { Header, Footer, Sider, Content } = Layout;

const headerStyle = {
  textAlign: 'center',
  color: '#fff',
  height: 64,
  paddingInline: 48,
  lineHeight: '64px',
  backgroundColor: '#4096ff',
};
const contentStyle = {
  textAlign: 'center',
  minHeight: 120,
  lineHeight: '120px',
  color: '#fff',
  backgroundColor: '#0958d9',
};
const siderStyle = {
  textAlign: 'center',
  lineHeight: '120px',
  color: '#fff',
  backgroundColor: '#1677ff',
};
const footerStyle = {
  textAlign: 'center',
  color: '#fff',
  backgroundColor: '#4096ff',
};
const layoutStyle = {
  borderRadius: 8,
  overflow: 'hidden',
  width: '100%',
  maxWidth: '100%',
};

function App() {
  return (
      <Flex gap="middle" wrap="wrap">

        <Layout style={layoutStyle}>
          <Header style={headerStyle}>Header</Header>
          <Layout>
            <Sider width="25%" style={siderStyle}>
              Menu
            </Sider>
            <Content style={contentStyle}><ServerUrl /></Content>
          </Layout>
          <Footer style={footerStyle}>Footer</Footer>
        </Layout>

      </Flex>
  );
}

export default App;
