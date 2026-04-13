ARG APP_INSIGHTS_AGENT_VERSION=3.7.7

# Application image

FROM hmctspublic.azurecr.io/base/java:21-distroless

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/prl-wa-task-configuration.jar /opt/app/

EXPOSE 4090
CMD [ "prl-wa-task-configuration.jar" ]
