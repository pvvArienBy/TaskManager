package by.it_academy.jd2.controllers.endpoints.web;

import by.it_academy.jd2.controllers.endpoints.web.ac.api.IAppContextAnnotation;
import by.it_academy.jd2.controllers.endpoints.web.ac.factory.AppContextAnnotationFactory;
import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.util.UserConvertUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/api/user")
public class UserServlet extends HttpServlet {

    private static final String USER_ID = "id";
    private static final String USER_VERSION = "version";
    private static final String TEXT_FILTER = "filter";

    private static  IUserService userService;

    private static ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        IAppContextAnnotation appContextAnnotation = AppContextAnnotationFactory.getInstance();
        userService = appContextAnnotation.getUserService();
        objectMapper =  appContextAnnotation.getObjectMapper();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String idSt = req.getParameter(USER_ID);
        String filter = req.getParameter(TEXT_FILTER);

        if (userService.validateCoordinatesParam(idSt) && filter == null) {
            Long id = Long.parseLong(idSt);
            UserDTO dto = UserConvertUtil.toDTO(userService.get(id));

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(objectMapper.writeValueAsString(dto));

        } else if (filter != null && idSt == null) {
            List<UserDTO> departmentDTOS = new ArrayList<>();

            for (UserEntity entity : userService.filterByName(filter)) {
                departmentDTOS.add(UserConvertUtil.toDTO(entity));
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(objectMapper.writeValueAsString(departmentDTOS));

        } else {
            List<UserDTO> departmentDTOS = new ArrayList<>();

            for (UserEntity entity : userService.get()) {
                departmentDTOS.add(UserConvertUtil.toDTO(entity));
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(objectMapper.writeValueAsString(departmentDTOS));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        InputStream requestBody = req.getInputStream();

        UserCreateDTO departmentCreateDTO = objectMapper.readValue(requestBody, UserCreateDTO.class);
        UserDTO departmentDTO = UserConvertUtil.toDTO(userService.add(departmentCreateDTO));

        resp.setStatus(HttpServletResponse.SC_CREATED);
        writer.write(objectMapper.writeValueAsString(departmentDTO) + "- ADD DEPARTMENT");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter(USER_ID);
        String versionStr = req.getParameter(USER_VERSION);
        InputStream requestBody = req.getInputStream();

        UserCreateDTO departmentDTO;

        try {
            departmentDTO = objectMapper.readValue(requestBody, UserCreateDTO.class);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("Проверьте данные, возможно вы не указали родителя!");
            return;
        }
        if (userService.validateCoordinatesParam(idStr) && userService.validateCoordinatesParam(versionStr)) {
            Long id = Long.parseLong(idStr);
            Long version = Long.parseLong(versionStr);
            CoordinatesDTO depCoordinatesDTO = new CoordinatesDTO(id,version);


            userService.update(depCoordinatesDTO, departmentDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            UserDTO newDTO = UserConvertUtil.toDTO(userService.get(id));

            writer.write(objectMapper.writeValueAsString(newDTO) + " - UPDATE DEPARTMENT");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter(USER_ID);
        String versionStr = req.getParameter(USER_VERSION);

        if (userService.validateCoordinatesParam(idStr) && userService.validateCoordinatesParam(versionStr)) {
            Long id = Long.parseLong(idStr);
            Long version = Long.parseLong(versionStr);
            CoordinatesDTO depCoordinatesDTO = new CoordinatesDTO(id,version);

                userService.remove(depCoordinatesDTO);

                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("Департамент удалён");
        } else {
            writer.write("Введите корректные данные");
        }
    }
}
